import { useState, useEffect } from 'react'
import api from '@/api/api'

export const useLessonProgress = (courseID) => {
  const [progress, setProgress] = useState([null])

  const fetchProgress = async () => {
    try {
      const response = await api.get(`/api/user/course-progress/${courseID}`)
      setProgress(response.data)
    } catch (error) {
      console.log('erro do hook', error)
    }
  }

  useEffect(() => {
    if (courseID) {
      // Verifica se courseID está definido
      fetchProgress()
    }
  }, [courseID])

  return { progress, fetchProgress }
}
