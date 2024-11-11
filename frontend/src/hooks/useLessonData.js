import { useState, useEffect } from 'react'
import { safeAwait } from '@/lib/safeAwait'
import axios from 'axios'

export const useLessonData = (slugCourse) => {
  const [lessonData, setLessonData] = useState([])
  const [error, setError] = useState(false)
  const [loading, setLoading] = useState(true)
  const [courseId, setCourseId] = useState()

  useEffect(() => {
    const fetchData = async () => {
      setError(false)
      setLoading(true)

      const [errorData, courseData] = await safeAwait(
        axios.get(`/api/courses/get-all/overviews`),
      )

      const [errorResponse, response] = await safeAwait(
        axios.get(`/api/courses/get-all/lessons/${slugCourse}`),
      )
      if (errorData) {
        setError(true)
        console.error(errorData)
        return
      }
      if (errorResponse) {
        setError(true)
        console.error(errorResponse)
        return
      }

      const course = courseData.data.find(
        (course) => course.slugCourse === slugCourse,
      )

      setCourseId(course.id)
      if (response.data == []) {
        setError(true)
      }
      setLessonData(response.data)

      setLoading(false)
    }
    fetchData()
  }, [slugCourse])

  return { lessonData, courseId, error, loading }
}
