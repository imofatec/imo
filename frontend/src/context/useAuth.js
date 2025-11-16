import { createContext, useContext, useState, useEffect } from 'react'
import authAxiosInstance from '@/api/authAxiosInstance'
import { Outlet, Navigate } from 'react-router-dom'

const AuthContext = createContext({ isAuth: false, isLoading: true })

export default function ProtectedRoute() {
  const [isAuth, setIsAuth] = useState(false)
  const [authLoading, setIsLoading] = useState(true)

  useEffect(() => {
    const auth = async () => {
      try {
        await authAxiosInstance.get('/api/user/private')
        setIsAuth(true)
        console.log('foi')
      } catch (err) {
        setIsAuth(false)
      } finally {
        setIsLoading(false)
      }
    }
    auth()
  }, [])

  if (!authLoading && !isAuth) {
    return <Navigate to="/login" replace />
  }

  return (
    <AuthContext.Provider value={{ isAuth, authLoading }}>
      <Outlet />
    </AuthContext.Provider>
  )
}

export function useAuth() {
  return useContext(AuthContext)
}
