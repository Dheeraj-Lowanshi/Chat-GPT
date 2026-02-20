import { createContext, useContext, useMemo, useState } from 'react'
import api from '../api/client'

const AuthContext = createContext(null)

export function AuthProvider({ children }) {
  const [user, setUser] = useState(() => JSON.parse(localStorage.getItem('votezy_user') || 'null'))

  const login = async (payload) => {
    const { data } = await api.post('/auth/login', payload)
    setUser(data)
    localStorage.setItem('votezy_user', JSON.stringify(data))
  }

  const logout = () => {
    setUser(null)
    localStorage.removeItem('votezy_user')
  }

  const value = useMemo(() => ({ user, login, logout }), [user])
  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>
}

export const useAuth = () => useContext(AuthContext)
