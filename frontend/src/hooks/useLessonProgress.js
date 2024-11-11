import { useState, useEffect, useCallback } from 'react'
import { safeAwait } from '@/lib/safeAwait'
import api from '@/api/api'

export const useLessonProgress = (courseId) => {
  const [progress, setProgress] = useState([null])
  const [cansei, setCansei] = useState(false)

  const fetchProgress = useCallback(async () => {
    if (!courseId) return

    const [error, result] = await safeAwait(
      api.get(`/api/user/course-progress/${courseId}`),
    )
    if (error) {
      setCansei(true)
      console.error('Erro ao buscar progresso do curso:', error)
      return
    }
    setProgress(result.data)
  }, [courseId])

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
    await fetchProgress()
    return true
  }, [courseId, fetchProgress])

  useEffect(() => {
    fetchProgress()
    console.log(cansei)
  }, [courseId, cansei, fetchProgress])

  return { progress, updateProgress, cansei }
}
