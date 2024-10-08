import { useState, useEffect } from 'react'
import api from '@/api/api'

export const useCoursesProgress = (slugCourse) => {
  const [progress, setProgress] = useState([null])

  const fetchProgress = async () => {
    try {
      const response = await api.get(`/api/user/courses-progress`, {
        params: { page: 0, size: 10 },
      })
      const progressList = response.data
      const courseProgress = progressList.find(
        (progress) => progress.courseOverview.slugCourse === slugCourse,
      )
      setProgress(courseProgress)
    } catch (error) {
      console.log('Erro ao obter progresso', error)
    }
  }

  useEffect(() => {
    fetchProgress()
  }, [slugCourse])

  return { progress, fetchProgress }
}
