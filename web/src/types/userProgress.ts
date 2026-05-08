import type { Course } from '@/types/course'
import type { UserSummary } from '@/types/user'

export type ProgressStatus = 'NOT_STARTED' | 'IN_PROGRESS' | 'FINISHED'

export type ProgressPeriod = {
  startedAt: string | null
  finishedAt: string | null
}

export type Progress = {
  id: string
  userId: string
  courseId: string
  lessonsWatched: string[]
  lessonsCount: number
  status: ProgressStatus
  progressPeriod: ProgressPeriod
}

export type Lesson = {
  id: string
  title: string
  description: string
  youtubeLink: string
  indexInCourse: number
  courseId: string
}

export type ProgressSummary = {
  watchedLessonsCount: number
  totalLessonsCount: number
  completionPercentage: number
}

export type UserProgress = {
  progress: Progress
  user: UserSummary
  course: Course
  lessons: Lesson[]
  summary: ProgressSummary
}

export type CurrentProgress = UserProgress
