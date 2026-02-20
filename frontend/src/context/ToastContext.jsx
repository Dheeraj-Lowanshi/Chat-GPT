import { createContext, useContext, useMemo, useState } from 'react'

const ToastContext = createContext(null)

export function ToastProvider({ children }) {
  const [message, setMessage] = useState(null)
  const notify = (text, type = 'success') => {
    setMessage({ text, type })
    setTimeout(() => setMessage(null), 2500)
  }

  const value = useMemo(() => ({ notify }), [])
  return (
    <ToastContext.Provider value={value}>
      {children}
      {message && <div className={`fixed top-4 right-4 px-4 py-2 rounded text-white ${message.type === 'error' ? 'bg-red-500' : 'bg-green-600'}`}>{message.text}</div>}
    </ToastContext.Provider>
  )
}

export const useToast = () => useContext(ToastContext)
