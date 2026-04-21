import { useCallback, useEffect, useState } from 'react'
import authAxiosInstance from '@/api/authAxiosInstance'
import { extractPaginatedArray, type PaginationInfo } from '@/lib/pagination'
import { safeAwait } from '@/lib/safeAwait'
import type { Course } from '@/types/course'

type MatchType = 'PERFECT' | 'CONTAINS' | 'STARTS_WITH'
type CombineWith = 'AND' | 'OR'

type UseCoursesParams = {
  matchType?: MatchType
  combineWith?: CombineWith
  page?: number
  size?: number
  enabled?: boolean
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
  enabled = true,
  categorySlug,
  name,
  nameSlug,
  contributorId,
}: UseCoursesParams = {}) {
  const [courses, setCourses] = useState<Course[]>([])
  const [pagination, setPagination] = useState<PaginationInfo | null>(null)
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState<string | null>(null)

  const fetchCourses = useCallback(async () => {
    if (!enabled) {
      setCourses([])
      setPagination(null)
      setError(null)
      setLoading(false)
      return
    }

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

    const [err, response] = await safeAwait(authAxiosInstance.get<unknown>(`/api/course/search?${query}`))

    if (err || !response) {
      setError(err?.message || 'Erro ao buscar cursos')
      setLoading(false)
      return
    }

    const { data, pagination: paginationInfo } = extractPaginatedArray<Course>(response.data)

    setCourses(data)
    setPagination(paginationInfo)
    setError(null)
    setLoading(false)
  }, [matchType, combineWith, page, size, enabled, categorySlug, name, nameSlug, contributorId])

  useEffect(() => {
    // eslint-disable-next-line
    void fetchCourses()
  }, [fetchCourses])

  return { courses, pagination, loading, error, refetch: fetchCourses }
}
