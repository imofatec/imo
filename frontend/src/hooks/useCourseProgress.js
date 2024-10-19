import api from '@/api/api'
import { safeAwait } from '@/lib/safeAwait'
import { useState } from 'react'

export const useCoursesProgress = () => {
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState(false)

  const fetchStartCourse = async (id) => {
    setError(false)
    setLoading(true)

    const [error, result] = await safeAwait(
      api.get(`/api/user/courses-progress`),
    )
    if (error) {
      setError(true)
      console.error('Erro ao buscar progresso dos cursos:', error)
      return
    }
    const progressList = result.data

    //Verificando se o curso já existe no progresso
    const courseProgress = progressList.find((progress) => progress.id === id)
    //Se o curso não estiver em andamento, inicia ele
    if (!courseProgress) {
      await api.put(`/api/user/update-progress/${id}`)
    }

    setLoading(false)
  }

  return { fetchStartCourse, loading, error }
}
