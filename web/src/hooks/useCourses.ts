import { useCallback, useEffect, useState } from 'react'
import authAxiosInstance from '@/api/authAxiosInstance'
import { safeAwait } from '@/lib/safeAwait'
import type { Course } from '@/types/course'

type MatchType = 'PERFECT' | 'CONTAINS' | 'STARTS_WITH'
type CombineWith = 'AND' | 'OR'

type UseCoursesParams = {
  matchType?: MatchType
  combineWith?: CombineWith
  page?: number
  size?: number
  categorySlug?: string
  name?: string
  nameSlug?: string
  contributorId?: string
}

export function useCourses({
  matchType = 'PERFECT',
  combineWith = 'AND',
  page = 0,
  size = 10,
  categorySlug,
  name,
  nameSlug,
  contributorId,
}: UseCoursesParams = {}) {
  const [courses, setCourses] = useState<Course[]>([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState<string | null>(null)

  const fetchCourses = useCallback(async () => {
    setLoading(true)

    const query = new URLSearchParams({
      matchType,
      combineWith,
      page: String(page),
      size: String(size),
      ...(categorySlug ? { categorySlug } : {}),
      ...(name ? { name } : {}),
      ...(nameSlug ? { nameSlug } : {}),
      ...(contributorId ? { contributorId } : {}),
    }).toString()

    const [err, response] = await safeAwait(
      authAxiosInstance.get<Course[]>(`/api/course/search?${query}`)
    )

    if (err || !response) {
      setError(err?.message || 'Erro ao buscar cursos')
      setLoading(false)
      return
    }

    setCourses(response.data || [])
    setError(null)
    setLoading(false)
  }, [matchType, combineWith, page, size, categorySlug, name, nameSlug, contributorId])

  useEffect(() => {
    // eslint-disable-next-line
    void fetchCourses()
  }, [fetchCourses])

  return { courses, loading, error, refetch: fetchCourses }
}
