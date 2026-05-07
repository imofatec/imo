export type CourseTerm = {
  name: string
  slug: string
}

export type Course = {
  id: string
  name: CourseTerm
  level: CourseTerm
  category: CourseTerm
  description: string
  firstLessonYoutubeLink: string
  lessonsCount: number
  skillIds?: string[]
  isActive: boolean
}

export type CourseDetailsLesson = {
  id: string
  createdAt: string
  updatedAt: string
  courseId: string
  indexInCourse: number
  title: string
  description: string
  youtubeLink: string
}

export type CourseDetails = {
  course: Course
  lessons: CourseDetailsLesson[]
}
