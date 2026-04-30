import axiosInstance from '@/api/axiosInstance'
import authAxiosInstance from '@/api/authAxiosInstance'
import { getSocialProfileMockContent } from '@/constants/socialProfileMocks'
import { safeAwait } from '@/lib/safeAwait'
import type { User } from '@/types/user'
import { useCallback, useEffect, useState } from 'react'

type UseSocialProfilePageParams = {
  userId: string | undefined
}

async function fetchUserById(userId: string) {
  const query = new URLSearchParams({ ids: userId }).toString()
  const endpoint = `/api/user/ids?${query}`

  const [publicError, publicResponse] = await safeAwait(axiosInstance.get<User[]>(endpoint))

  if (!publicError && publicResponse) {
    return publicResponse.data?.[0] ?? null
  }

  const [authError, authResponse] = await safeAwait(authAxiosInstance.get<User[]>(endpoint))

  if (authError || !authResponse) {
    throw authError ?? publicError ?? new Error('Erro ao buscar perfil do usuario')
  }

  return authResponse.data?.[0] ?? null
}

export function useSocialProfilePage({ userId }: UseSocialProfilePageParams) {
  const [user, setUser] = useState<User | null>(null)
  const [isLoading, setIsLoading] = useState(true)
  const [error, setError] = useState<string | null>(null)
  const mockContent = getSocialProfileMockContent(userId)

  const fetchProfile = useCallback(async () => {
    if (!userId) {
      setUser(null)
      setError('Perfil invalido')
      setIsLoading(false)
      return
    }

    setIsLoading(true)

    try {
      const responseUser = await fetchUserById(userId)

      if (!responseUser) {
        setUser(null)
        setError('Perfil nao encontrado')
        setIsLoading(false)
        return
      }

      setUser(responseUser)
      setError(null)
    } catch (requestError) {
      const message =
        requestError instanceof Error ? requestError.message : 'Erro ao buscar perfil do usuario'

      setUser(null)
      setError(message)
    } finally {
      setIsLoading(false)
    }
  }, [userId])

  useEffect(() => {
    void fetchProfile()
  }, [fetchProfile])

  return {
    user,
    isLoading,
    error,
    bio: mockContent.bio,
    featuredAchievements: mockContent.featuredAchievements,
    refetch: fetchProfile,
  }
}
