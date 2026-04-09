export type EditLessonData = {
  nameLesson: string
  link: string
  descriptionL: string
}

export type EditCourseData = {
  nameCourse: string
  category: string
  level: string
  description: string
  lessons: EditLessonData[]
}
