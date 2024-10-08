import { useState, useEffect } from 'react'
import axios from 'axios'

export const useLessonData = (slugCourse) => {
  const [lessonData, setLessonData] = useState([])
  const [error, setError] = useState(false)
  const [loading, setLoading] = useState(true)
  const [courseID, setCourseId] = useState()

  useEffect(() => {
    const fetchData = async () => {
      setError(false)
      setLoading(true)
      try {
        const response = await axios.get(
          `/api/courses/get-all/lessons/${slugCourse}`,
        )
        const courseDate = await axios.get(`/api/courses/get-all/overviews`)

        const course = courseDate.data.find(
          (course) => course.slugCourse === slugCourse,
        )
        setCourseId(course.id)
        setLessonData(response.data)
      } catch (error) {
        setError(true)
        console.error(error)
      } finally {
        setLoading(false)
      }
    }
    fetchData()
  }, [slugCourse])

  return { lessonData, courseID, error, loading }
}
