import api from '@/api/api'
import { safeAwait } from '@/lib/safeAwait'
import { useEffect, useState } from 'react'

export const useMyCourses = (selectedCategory, page, size) => {
  const [error, setError] = useState(null)
  const [loading, setLoading] = useState(true)
  const [myCourses, setMycourses] = useState()
  const [hasMoreCourses, setHasMoreCourses] = useState(true)

  useEffect(() => {
    const fetchMyCourses = async () => {
      setError(false)
      setLoading(true)

      const statusFilter =
        selectedCategory === 'finalizados'
          ? 'FINISHED'
          : selectedCategory === 'em-andamento'
            ? 'IN_PROGRESS'
            : null

      const [error, result] = await safeAwait(
        api.get('/api/user/course-overviews', {
          params: { page, size },
        }),
      )

      if (error) {
        console.error('Erro ao buscar progresso dos cursos:', error)
        setError(true)
        return
      }
      const filteredCourses = result.data.filter((course) =>
        statusFilter ? course.status === statusFilter : true,
      )

      setMycourses(filteredCourses)
      setLoading(false)

      setHasMoreCourses(filteredCourses.length === size)
    }

    fetchMyCourses()
  }, [selectedCategory, page, size])

  return { myCourses, error, loading, hasMoreCourses }
}
