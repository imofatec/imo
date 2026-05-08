import { createContext, useContext, useEffect, useState } from 'react'
import { isTokenValid } from '@/lib/isTokenValid'

type AuthContextType = {
  token: string | null
  isAuthenticated: boolean
  isLoading: boolean
  login: (token: string) => void
  logout: () => void
}

const AuthContext = createContext<AuthContextType | null>(null)

export function AuthProvider({ children }: { children: React.ReactNode }) {
  const [token, setToken] = useState<string | null>(null)
  const [isLoading, setIsLoading] = useState(true)

  useEffect(() => {
    const storedToken = localStorage.getItem('token')

    if (isTokenValid(storedToken)) {
      setToken(storedToken)
    } else {
      localStorage.removeItem('token')
      setToken(null)
    }

    setIsLoading(false)
  }, [])

  useEffect(() => {
    if (!token) return

    if (!isTokenValid(token)) {
      localStorage.removeItem('token')
      setToken(null)
    }
  }, [token])

  const login = (newToken: string) => {
    localStorage.setItem('token', newToken)
    setToken(newToken)
  }

  const logout = () => {
    localStorage.removeItem('token')
    setToken(null)
  }

  const isAuthenticated = isTokenValid(token)

  return (
    <AuthContext.Provider value={{ token, isAuthenticated, isLoading, login, logout }}>
      {children}
    </AuthContext.Provider>
  )
}

export function useAuth() {
  const context = useContext(AuthContext)

  if (!context) {
    throw new Error('useAuth deve ser usado com o AuthProvider')
  }

  return context
}
