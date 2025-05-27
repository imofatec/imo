import authAxiosInstance from '@/api/authAxiosInstance'
import { safeAwait } from '@/lib/safeAwait'
import { useEffect, useState } from 'react'

export const useCourseById = (id) => {
  const [error, setError] = useState(null)
  const [loading, setLoading] = useState(true)
  const [courseById, setCourseById] = useState()
    const fetchCourseById = async () => {
      if (!id) return;
      setError(false)
      setLoading(true)

      const [error, result] = await safeAwait(
        authAxiosInstance.get(`/api/courses/${id}`),
      )

      if (error) {
        console.error('Erro ao buscar id do curso:', error)
        setError(true)
        return
      }
      
      setCourseById(result.data)
      setLoading(false)

    }
  useEffect(() => {


    fetchCourseById()
  }, [id])

  return { courseById, error, loading, fetchCourseById }
}