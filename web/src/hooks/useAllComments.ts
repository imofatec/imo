import { useCallback, useEffect, useState } from 'react'
import authAxiosInstance from '@/api/authAxiosInstance'
import { safeAwait } from '@/lib/safeAwait'
import type { LessonComment } from '@/types/watch'

type UseAllCommentsParams = {
  page?: number
  size?: number
  enabled?: boolean
}

export function useAllComments(
  lessonId: string,
  { page = 0, size = 10, enabled = true }: UseAllCommentsParams = {}
) {
  const [comments, setComments] = useState<LessonComment[]>([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState<string | null>(null)

  const fetchComments = useCallback(async () => {
    if (!enabled || !lessonId) {
      setComments([])
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
      authAxiosInstance.get<LessonComment[]>(`/api/comment/${lessonId}?${query}`)
    )

    if (err || !response) {
      setError(err?.message || 'Erro ao buscar comentarios')
      setLoading(false)
      return
    }

    setComments(response.data)
    setError(null)
    setLoading(false)
  }, [page, size, enabled, lessonId])

  useEffect(() => {
    // eslint-disable-next-line
    void fetchComments()
  }, [fetchComments])

  return { comments, loading, error, refetch: fetchComments }
}
