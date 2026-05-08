import { useCallback, useEffect, useState } from 'react'
import authAxiosInstance from '@/api/authAxiosInstance'
import { extractPaginatedArray, type PaginationInfo } from '@/lib/pagination'
import { safeAwait } from '@/lib/safeAwait'
import type { UserProgress } from '@/types/userProgress'

type UseUserProgressParams = {
  page?: number
  size?: number
}

export function useUserProgress({ page = 0, size = 10 }: UseUserProgressParams = {}) {
  const [userProgress, setUserProgress] = useState<UserProgress[]>([])
  const [pagination, setPagination] = useState<PaginationInfo | null>(null)
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState<string | null>(null)

  const fetchUserProgress = useCallback(async () => {
    setLoading(true)

    const query = new URLSearchParams({
      page: String(page),
      size: String(size),
    }).toString()

    const [err, response] = await safeAwait(
      authAxiosInstance.get<unknown>(`/api/progress/details?${query}`)
    )

    if (err || !response) {
      setError(err?.message || 'Erro ao buscar progresso do usuário')
      setLoading(false)
      return
    }

    const { data, pagination: paginationInfo } = extractPaginatedArray<UserProgress>(response.data)

    setUserProgress(data)
    setPagination(paginationInfo)
    setError(null)
    setLoading(false)
  }, [page, size])

  useEffect(() => {
    // eslint-disable-next-line
    void fetchUserProgress()
  }, [fetchUserProgress])

  return {
    userProgress,
    pagination,
    loading,
    error,
    refetch: fetchUserProgress,
  }
}
