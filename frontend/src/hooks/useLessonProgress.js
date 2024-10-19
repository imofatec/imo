import { useState, useEffect } from 'react'
import { safeAwait } from '@/lib/safeAwait'
import api from '@/api/api'

export const useLessonProgress = (courseID) => {
  const [progress, setProgress] = useState([null])

  const fetchProgress = async () => {
    const [error, result] = await safeAwait(
      api.get(`/api/user/course-progress/${courseID}`),
    )
    if (error) {
      console.error('Erro ao buscar progresso do curso:', error)
      return
    }
    setProgress(result.data)
  }

  useEffect(() => {
    if (courseID) {
      fetchProgress()
    }
  }, [courseID])

  return { progress, fetchProgress }
}
