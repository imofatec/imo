import { useEffect, useState } from 'react'

export const useSortedCourses = (courses, order) => {
  const [sortedCourses, setSortedCourses] = useState([])

  useEffect(() => {
    if (!courses) {
      setSortedCourses([])
      return
    }

    let sortedCourses = [...courses]

    switch (order) {
      case 'oldest':
        sortedCourses.sort(
          (a, b) =>
            new Date(a.courseOverview.createdAt) -
            new Date(b.courseOverview.createdAt),
        )
        break
      case 'newest':
        sortedCourses.sort(
          (a, b) =>
            new Date(b.courseOverview.createdAt) -
            new Date(a.courseOverview.createdAt),
        )
        break
      case 'az':
        sortedCourses.sort((a, b) =>
          a.courseOverview.name.localeCompare(b.courseOverview.name),
        )
        break
      case 'za':
        sortedCourses.sort((a, b) =>
          b.courseOverview.name.localeCompare(a.courseOverview.name),
        )
        break
      default:
        break
    }

    setSortedCourses(sortedCourses)
  }, [courses, order])

  return sortedCourses
}
