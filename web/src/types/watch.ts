export type WatchTab = 'lessons' | 'comments'

export type WatchLesson = {
  id: string
  order: number
  title: string
  description: string
  youtubeId: string
  duration: string
  watched: boolean
}

export type LessonComment = {
  id: string
  author: string
  message: string
  createdAt: string
}
