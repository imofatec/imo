import { useState, useEffect, useCallback } from 'react'
import { safeAwait } from '@/lib/safeAwait'
import api from '@/api/api'

export const useLessonProgress = (courseId) => {
  const [progress, setProgress] = useState([])
  const [cansei, setCansei] = useState(false)
  const [loadingProgress, setLoadingProgress] = useState(true)

  const updateProgress = useCallback(async () => {
    if (!courseId) return

    const [error] = await safeAwait(
      api.put(`/api/user/update-progress/${courseId}`),
    )

    if (error) {
      setCansei(true)
      console.error('Erro ao marcar a aula como concluída:', error)
      return
    }
  }, [courseId])

  const fetchProgress = useCallback(async () => {
    if (!courseId) return

    const [error, result] = await safeAwait(
      api.get(`/api/user/course-progress/${courseId}`),
    )

    if (error?.status === 404) {
      await updateProgress()

      // fodase kkkkkkkkkk
      return fetchProgress()
    }

    setProgress(result.data)
    setLoadingProgress(false)
  }, [courseId, updateProgress])

  useEffect(() => {
    fetchProgress()
  }, [courseId, cansei, fetchProgress])

  return { fetchProgress, progress, cansei, loadingProgress, updateProgress }
}
