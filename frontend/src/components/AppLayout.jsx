import { Link } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'

export default function AppLayout({ children }) {
  const { user, logout } = useAuth()
  return (
    <div className="min-h-screen">
      <header className="bg-indigo-700 text-white p-4">
        <div className="max-w-6xl mx-auto flex justify-between items-center">
          <Link to="/" className="font-bold text-xl">Votezy Lite</Link>
          <nav className="flex gap-4 text-sm">
            <Link to="/elections">Elections</Link>
            {!user && <Link to="/voter/auth">Voter</Link>}
            {!user && <Link to="/candidate/auth">Candidate</Link>}
            {!user && <Link to="/admin/login">Admin</Link>}
            {user?.role === 'ADMIN' && <Link to="/admin/dashboard">Dashboard</Link>}
            {user && <button onClick={logout}>Logout</button>}
          </nav>
        </div>
      </header>
      <main className="max-w-6xl mx-auto p-4">{children}</main>
    </div>
  )
}
