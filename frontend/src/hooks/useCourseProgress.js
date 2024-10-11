import api from '@/api/api'
import { useState } from 'react'

export const useCoursesProgress = () => {
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState(false)

  const fetchStartCourse = async (id) => {
    setError(false)
    setLoading(true)
    try {
      //Obtendo o progresso dos cursos
      const response = await api.get(`/api/user/courses-progress`)
      const progressList = response.data

      //Verificando se o curso já existe no progresso
      const courseProgress = progressList.find(
        (progress) => progress.id === id,
      )

      //Se o curso não estiver em andamento, inicia ele
      if (!courseProgress) {
        await api.put(`/api/user/update-progress/${id}`)
      }
    } catch (error) {
      setError(true)
      console.log('ERROR', error.response)
    } finally {
      setLoading(false)
    }
  }

  return { fetchStartCourse, loading, error }
}
