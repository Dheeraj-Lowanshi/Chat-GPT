import { useForm } from 'react-hook-form'
import { z } from 'zod'
import { zodResolver } from '@hookform/resolvers/zod'
import { useNavigate } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'
import { useToast } from '../context/ToastContext'

const schema = z.object({
  email: z.string().email(),
  password: z.string().min(6),
})

export default function AdminLoginPage() {
  const { login } = useAuth()
  const { notify } = useToast()
  const navigate = useNavigate()
  const { register, handleSubmit, formState: { errors } } = useForm({ resolver: zodResolver(schema) })

  const onSubmit = async (values) => {
    try {
      await login({ ...values, role: 'ADMIN' })
      notify('Admin login successful')
      navigate('/admin/dashboard')
    } catch {
      notify('Invalid login', 'error')
    }
  }

  return <form onSubmit={handleSubmit(onSubmit)} className="max-w-md mx-auto bg-white p-6 rounded shadow space-y-3">
    <h2 className="text-xl font-semibold">Admin Login</h2>
    <input {...register('email')} placeholder="Email" className="w-full border p-2 rounded" />
    {errors.email && <p className="text-red-500 text-sm">{errors.email.message}</p>}
    <input type="password" {...register('password')} placeholder="Password" className="w-full border p-2 rounded" />
    {errors.password && <p className="text-red-500 text-sm">{errors.password.message}</p>}
    <button className="w-full bg-indigo-600 text-white p-2 rounded">Login</button>
  </form>
}
