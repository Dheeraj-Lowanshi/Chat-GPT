import { useState } from 'react'
import { useForm } from 'react-hook-form'
import { z } from 'zod'
import { zodResolver } from '@hookform/resolvers/zod'
import api from '../api/client'
import { useAuth } from '../context/AuthContext'
import { useToast } from '../context/ToastContext'

const registerSchema = z.object({
  fullName: z.string().min(3), email: z.string().email(), password: z.string().min(6),
  phoneNumber: z.string().min(10), dateOfBirth: z.string(), gender: z.enum(['MALE', 'FEMALE', 'OTHER']),
  address: z.string().min(5), aadharNumber: z.string().min(12), partyName: z.string().min(2), manifesto: z.string().min(10), electionId: z.coerce.number()
})

export default function CandidateAuthPage() {
  const [isRegister, setIsRegister] = useState(true)
  const { login } = useAuth()
  const { notify } = useToast()
  const { register, handleSubmit, formState: { errors } } = useForm({ resolver: zodResolver(registerSchema) })

  const onRegister = async (values) => {
    try {
      const formData = new FormData()
      formData.append('data', new Blob([JSON.stringify(values)], { type: 'application/json' }))
      await api.post('/candidates', formData)
      notify('Candidate registered. Awaiting approval.')
    } catch {
      notify('Registration failed', 'error')
    }
  }

  const onLogin = async (e) => {
    e.preventDefault()
    const form = new FormData(e.currentTarget)
    try {
      await login({ email: form.get('email'), password: form.get('password'), role: 'CANDIDATE' })
      notify('Candidate logged in')
    } catch {
      notify('Login failed', 'error')
    }
  }

  return <div className="space-y-4">
    <div className="flex gap-2"><button className="px-3 py-1 bg-indigo-600 text-white rounded" onClick={() => setIsRegister(true)}>Register</button><button className="px-3 py-1 bg-slate-200 rounded" onClick={() => setIsRegister(false)}>Login</button></div>
    {isRegister ? <form onSubmit={handleSubmit(onRegister)} className="grid md:grid-cols-2 gap-3 bg-white p-4 rounded shadow">
      {['fullName','email','password','phoneNumber','dateOfBirth','gender','address','aadharNumber','partyName','manifesto','electionId'].map((f) => <input key={f} type={f==='password'?'password':'text'} placeholder={f} {...register(f)} className="border p-2 rounded" />)}
      {Object.values(errors).length > 0 && <p className="text-red-500 text-sm md:col-span-2">Please fix form errors.</p>}
      <button className="md:col-span-2 bg-indigo-600 text-white p-2 rounded">Submit</button>
    </form> : <form onSubmit={onLogin} className="max-w-md bg-white p-4 rounded shadow space-y-2"><input name="email" className="w-full border p-2 rounded" placeholder="Email"/><input name="password" type="password" className="w-full border p-2 rounded" placeholder="Password"/><button className="w-full bg-indigo-600 text-white p-2 rounded">Login</button></form>}
  </div>
}
