import axiosInstance from '@/api/axiosInstance'
import { safeAwait } from '@/lib/safeAwait'
import { useEffect, useState } from 'react'

export const useCoursesData = (slug,level, page, size, setPage) => {
  const [error, setError] = useState(false)
  const [loading, setLoading] = useState(true)
  const [categories, setCategories] = useState([])
  const [courses, setCourses] = useState([])
  const [hasMoreCourses, setHasMoreCourses] = useState(true)
  const [currentSlug, setCurrentSlug] = useState(slug)
  const [currentLevel, setCurrentLevel] = useState(level)

  const fetchData = async () => {
    setError(false)
    setLoading(true)

    const [errorCategories, responseCategories] = await safeAwait(
      axiosInstance.get('/api/courses/categories'),
    )
    const [errorCourses, responseCourses] = await safeAwait(
      currentSlug
        ? await axiosInstance.get(
            `/api/courses/pagination/overviews/categories/${currentSlug}`,
            {
              params: { page, size },
            },
          )
        : currentLevel
        ? await axiosInstance.get(
            `/api/courses/pagination/overviews/level/${currentLevel}`,
            {
              params: { page, size },
            },
          )
        : await axiosInstance.get('/api/courses/pagination/overviews', {
            params: { page, size },
          }),
    )

    if (errorCategories) {
      setError(true)
      console.error(errorCategories)
      return
    }
    if (errorCourses) {
      setError(true)
      console.error(errorCourses)
      return
    }

    setCategories(responseCategories.data)

    setCourses(responseCourses.data)

    setHasMoreCourses(responseCourses.data.length >= size)

    setLoading(false)
  }

  useEffect(() => {
    setCurrentSlug(slug)
    setCurrentLevel(level)
    setPage(0)
    fetchData()
  }, [slug,level])

  useEffect(() => {
    fetchData()
  }, [currentSlug,currentLevel, page])

  return {
    categories,
    courses,
    hasMoreCourses,
    loading,
    error,
    fetchData,
    setCurrentSlug,
    setCurrentLevel,
  }
}
