import authAxiosInstance from '@/api/authAxiosInstance'
import { safeAwait } from '@/lib/safeAwait'
import type { CurrentProgress } from '@/types/userProgress'
import { useCallback, useEffect, useMemo, useState } from 'react'

export function useCurrentProgress(courseId: string) {
  const [currentProgress, setCurrentProgress] = useState<CurrentProgress | null>(null)
  const [markingLessonIds, setMarkingLessonIds] = useState<Set<string>>(() => new Set())
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState<string | null>(null)

  const fetchProgress = useCallback(async () => {
    if (!courseId) {
      setCurrentProgress(null)
      setLoading(false)
      return
    }

    setLoading(true)

    const [err, response] = await safeAwait(
      authAxiosInstance.get<CurrentProgress>(`/api/progress/details/${courseId}`)
    )

    if (err || !response) {
      setError(err?.message || 'Erro ao buscar progresso do curso')
      setLoading(false)
      return
    }

    setCurrentProgress(response.data)
    setError(null)
    setLoading(false)
  }, [courseId])

  useEffect(() => {
    // eslint-disable-next-line
    void fetchProgress()
  }, [fetchProgress])

  const watchedLessonIds = useMemo(
    () => new Set(currentProgress?.progress.lessonsWatched ?? []),
    [currentProgress]
  )

  const markLessonAsWatched = useCallback(
    async (lessonId: string) => {
      if (!lessonId || watchedLessonIds.has(lessonId)) {
        return true
      }

      setMarkingLessonIds((prev) => new Set(prev).add(lessonId))

      const [err] = await safeAwait(authAxiosInstance.put(`/api/progress/${lessonId}`))

      if (err) {
        setError(err.message || 'Erro ao marcar aula como assistida')
        setMarkingLessonIds((prev) => {
          const next = new Set(prev)
          next.delete(lessonId)
          return next
        })
        return false
      }

      setCurrentProgress((prev) => {
        if (!prev || prev.progress.lessonsWatched.includes(lessonId)) {
          return prev
        }

        return {
          ...prev,
          progress: {
            ...prev.progress,
            lessonsWatched: [...prev.progress.lessonsWatched, lessonId],
          },
        }
      })

      setMarkingLessonIds((prev) => {
        const next = new Set(prev)
        next.delete(lessonId)
        return next
      })

      await fetchProgress()
      setError(null)
      return true
    },
    [fetchProgress, watchedLessonIds]
  )

  return {
    currentProgress,
    watchedLessonIds,
    markingLessonIds,
    markLessonAsWatched,
    loading,
    error,
    refetch: fetchProgress,
  }
}
