import { useState, useEffect } from 'react'
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
    try {
      const responseCategories = await axios.get(
        '/api/courses/get-all/categories',
      )
      setCategories(responseCategories.data)

      const responseCourses = currentSlug
        ? await axios.get(
            `/api/courses/pagination/get-all/overviews/${currentSlug}`,
            {
              params: { page, size },
            },
          )
        : await axios.get('/api/courses/pagination/get-all/overviews', {
            params: { page, size },
          })

      setCourses(responseCourses.data)

      setHasMoreCourses(responseCourses.data.length >= size)
    } catch (error) {
      setError(true)
    } finally {
      setLoading(false)
    }
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
