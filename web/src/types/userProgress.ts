import type { Course } from '@/types/course'

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

export type UserProgressUser = {
  id: string
  name: string
  email: string
  isConfirmed: boolean
  profilePicturePath: string
  birthDate: string
  availableTimePerDay: string
  academicDegree: string
  experienceLevel: string
  categoriesOfInterest: string[]
}

export type Lesson = {
  id: string
  title: string
  description: string
  youtubeLink: string
  indexInCourse: number
  courseId: string
}

export type UserProgress = {
  progress: Progress
  user: UserProgressUser
  course: Course
  lessons: Lesson[]
}

export type CurrentProgress = UserProgress
