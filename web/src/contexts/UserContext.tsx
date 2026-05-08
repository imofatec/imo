import authAxiosInstance from '@/api/authAxiosInstance'
import { useAuth } from '@/contexts/AuthContext'
import { safeAwait } from '@/lib/safeAwait'
import type { CurrentUserProfile } from '@/types/user'
import { createContext, useCallback, useContext, useEffect, useMemo, useState } from 'react'

type UserContextType = {
  user: CurrentUserProfile | null
  loading: boolean
  error: string | null
  refetch: () => Promise<void>
  setUser: React.Dispatch<React.SetStateAction<CurrentUserProfile | null>>
}

const UserContext = createContext<UserContextType | null>(null)

export function UserProvider({ children }: { children: React.ReactNode }) {
  const { isAuthenticated, isLoading: isAuthLoading } = useAuth()
  const [user, setUser] = useState<CurrentUserProfile | null>(null)
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState<string | null>(null)

  const fetchUser = useCallback(async () => {
    if (!isAuthenticated) {
      setUser(null)
      setError(null)
      setLoading(false)
      return
    }

    setLoading(true)

    const [err, response] = await safeAwait(
      authAxiosInstance.get<CurrentUserProfile>('/api/user/profile')
    )

    if (err || !response) {
      setError(err?.message || 'Erro ao buscar perfil do usuário')
      setLoading(false)
      return
    }

    setUser(response.data ?? null)
    setError(null)
    setLoading(false)
  }, [isAuthenticated])

  useEffect(() => {
    if (isAuthLoading) {
      return
    }
    // eslint-disable-next-line
    void fetchUser()
  }, [fetchUser, isAuthLoading])

  const value = useMemo(
    () => ({
      user,
      loading,
      error,
      refetch: fetchUser,
      setUser,
    }),
    [user, loading, error, fetchUser]
  )

  return <UserContext.Provider value={value}>{children}</UserContext.Provider>
}
// eslint-disable-next-line
export function useUser() {
  const context = useContext(UserContext)

  if (!context) {
    throw new Error('useUser deve ser usado com o UserProvider')
  }

  return context
}
