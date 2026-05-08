import authAxiosInstance from '@/api/authAxiosInstance'
import { safeAwait } from '@/lib/safeAwait'
import type { CourseDetails } from '@/types/course'
import { useEffect, useState, useCallback } from 'react'

export function useCurrentCourse(courseId: string) {
  const [course, setCourse] = useState<CourseDetails | null>(null)
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState<string | null>(null)

  const fetchCourse = useCallback(async () => {
    setLoading(true)
    const [err, response] = await safeAwait(
      authAxiosInstance.get<CourseDetails>(`/api/course/details/${courseId}`)
    )

    if (err || !response) {
      setError(err?.message || 'Erro ao buscar curso')
      setLoading(false)
      return
    }

    setCourse(response.data)
    setError(null)
    setLoading(false)
  }, [courseId])

  useEffect(() => {
    // eslint-disable-next-line
    void fetchCourse()
  }, [fetchCourse])

  return {
    course,
    loading,
    error,
    refetch: fetchCourse,
  }
}
