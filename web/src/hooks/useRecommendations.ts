import { useCallback, useEffect, useState } from 'react'
import authAxiosInstance from '@/api/authAxiosInstance'
import { useAuth } from '@/contexts/AuthContext'
import { safeAwait } from '@/lib/safeAwait'
import type { Course } from '@/types/course'

export function useRecommendations() {
  const { isAuthenticated, isLoading: isAuthLoading } = useAuth()
  const [courses, setCourses] = useState<Course[]>([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState<string | null>(null)

  const fetchRecommendations = useCallback(async () => {
    if (isAuthLoading) return

    if (!isAuthenticated) {
      setCourses([])
      setError(null)
      setLoading(false)
      return
    }

    setLoading(true)

    const [err, response] = await safeAwait(
      authAxiosInstance.get<Course[]>('/api/recommendation/me')
    )

    if (err || !response) {
      setCourses([])
      setError(err?.message || 'Erro ao buscar recomendações')
      setLoading(false)
      return
    }

    setCourses(response.data || [])
    setError(null)
    setLoading(false)
  }, [isAuthenticated, isAuthLoading])

  useEffect(() => {
    // eslint-disable-next-line
    void fetchRecommendations()
  }, [fetchRecommendations])

  return {
    courses,
    error,
    isAuthenticated,
    loading,
    refetch: fetchRecommendations,
  }
}
