import authAxiosInstance from '@/api/authAxiosInstance'
import { useAuth } from '@/contexts/AuthContext'
import { extractPaginatedArray, type PaginationInfo } from '@/lib/pagination'
import { safeAwait } from '@/lib/safeAwait'
import type { UserProgress } from '@/types/userProgress'
import { useCallback, useEffect, useMemo, useState } from 'react'

type UseContinueProgressParams = {
  page?: number
  size?: number
}

export function useContinueProgress({ page = 0, size = 20 }: UseContinueProgressParams = {}) {
  const { isAuthenticated } = useAuth()
  const [progressList, setProgressList] = useState<UserProgress[]>([])
  const [pagination, setPagination] = useState<PaginationInfo | null>(null)
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState<string | null>(null)

  const fetchContinueProgress = useCallback(async () => {
    if (!isAuthenticated) {
      setProgressList([])
      setPagination(null)
      setError(null)
      setLoading(false)
      return
    }

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

    setProgressList(data)
    setPagination(paginationInfo)
    setError(null)
    setLoading(false)
  }, [isAuthenticated, page, size])

  useEffect(() => {
    // eslint-disable-next-line
    void fetchContinueProgress()
  }, [fetchContinueProgress])

  const continueProgress = useMemo(
    () =>
      progressList.find((item) => {
        const completion = item.summary.completionPercentage
        return completion > 0 && completion < 100
      }) ?? null,
    [progressList]
  )

  return {
    continueProgress,
    progressList,
    pagination,
    loading,
    error,
    refetch: fetchContinueProgress,
  }
}
