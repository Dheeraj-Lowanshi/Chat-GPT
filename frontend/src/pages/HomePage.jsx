import { Link } from 'react-router-dom'

export default function HomePage() {
  return (
    <div className="py-16 text-center space-y-4">
      <h1 className="text-4xl font-bold">Votezy Lite</h1>
      <p className="text-slate-600">Online Election Management System</p>
      <div className="flex justify-center gap-3">
        <Link className="px-4 py-2 bg-indigo-600 text-white rounded" to="/elections">View Elections</Link>
        <Link className="px-4 py-2 bg-slate-200 rounded" to="/voter/auth">Get Started</Link>
      </div>
    </div>
  )
}
