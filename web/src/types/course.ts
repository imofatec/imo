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
  isActive: boolean
}
