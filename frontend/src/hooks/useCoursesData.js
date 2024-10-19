import { useState, useEffect } from 'react'
import { safeAwait } from '@/lib/safeAwait'
import axios from 'axios'

export const useCoursesData = (slug, page, size, setPage) => {
  const [error, setError] = useState(false)
  const [loading, setLoading] = useState(true)
  const [categories, setCategories] = useState([])
  const [courses, setCourses] = useState([])
  const [hasMoreCourses, setHasMoreCourses] = useState(true)
  const [currentSlug, setCurrentSlug] = useState(slug)

  const fetchData = async () => {
    setError(false)
    setLoading(true)

    const [errorCategories, responseCategories] = await safeAwait(
      axios.get('/api/courses/get-all/categories'),
    )
    const [errorCourses, responseCourses] = await safeAwait(
      currentSlug
        ? await axios.get(
            `/api/courses/pagination/get-all/overviews/${currentSlug}`,
            {
              params: { page, size },
            },
          )
        : await axios.get('/api/courses/pagination/get-all/overviews', {
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
    setPage(0)
  }, [slug])

  useEffect(() => {
    fetchData()
  }, [currentSlug, page])

  return {
    categories,
    courses,
    hasMoreCourses,
    loading,
    error,
    fetchData,
    setCurrentSlug,
  }
}
