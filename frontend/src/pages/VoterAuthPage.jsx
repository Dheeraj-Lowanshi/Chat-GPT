import { useState } from 'react'
import { useForm } from 'react-hook-form'
import { z } from 'zod'
import { zodResolver } from '@hookform/resolvers/zod'
import api from '../api/client'
import { useAuth } from '../context/AuthContext'
import { useToast } from '../context/ToastContext'

const schema = z.object({
  fullName: z.string().min(3), email: z.string().email(), password: z.string().min(6), phoneNumber: z.string().min(10),
  dateOfBirth: z.string(), gender: z.enum(['MALE','FEMALE','OTHER']), address: z.string().min(5), aadharNumber: z.string().min(12),
  voterIdNumber: z.string().min(5), constituency: z.string().min(2)
})

export default function VoterAuthPage() {
  const [mode, setMode] = useState('register')
  const { login } = useAuth()
  const { notify } = useToast()
  const { register, handleSubmit } = useForm({ resolver: zodResolver(schema) })

  const onRegister = async (values) => {
    await api.post('/voters', values)
    notify('Voter registered successfully')
  }

  const onLogin = async (e) => {
    e.preventDefault()
    const form = new FormData(e.currentTarget)
    await login({ email: form.get('email'), password: form.get('password'), role: 'VOTER' })
    notify('Voter login successful')
  }

  return <div className="space-y-4">
    <div className="flex gap-2"><button className="px-3 py-1 bg-indigo-600 text-white rounded" onClick={()=>setMode('register')}>Register</button><button className="px-3 py-1 bg-slate-200 rounded" onClick={()=>setMode('login')}>Login</button></div>
    {mode==='register' ? <form onSubmit={handleSubmit(onRegister)} className="grid md:grid-cols-2 gap-3 bg-white p-4 rounded shadow">
      {['fullName','email','password','phoneNumber','dateOfBirth','gender','address','aadharNumber','voterIdNumber','constituency'].map((f)=><input key={f} type={f==='password'?'password':'text'} {...register(f)} placeholder={f} className="border p-2 rounded" />)}
      <button className="md:col-span-2 bg-indigo-600 text-white p-2 rounded">Register</button>
    </form> : <form onSubmit={onLogin} className="max-w-md bg-white p-4 rounded shadow space-y-2"><input name="email" placeholder="Email" className="w-full border p-2 rounded"/><input type="password" name="password" placeholder="Password" className="w-full border p-2 rounded"/><button className="w-full bg-indigo-600 text-white p-2 rounded">Login</button></form>}
  </div>
}
