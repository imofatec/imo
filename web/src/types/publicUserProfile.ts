export type PublicProfileHighlightedAchievement = {
  key: string
  title: string
  description: string
  imageUrl: string | null
  unlockedAt: string | null
}

export type PublicProfileRecentCourse = {
  courseId: string
  courseName: string
  firstLessonYoutubeLink: string
  watchedLessonsCount: number
  totalLessonsCount: number
}

export type PublicProfileLastComment = {
  content: string
  createdAt: string | null
  lessonTitle: string
  lessonYoutubeLink: string
  courseId: string
  courseName: string
}

export type PublicUserProfile = {
  name: string
  bio: string | null
  profilePicturePath: string | null
  categoriesOfInterest: string[] | null
  highlightedAchievements: PublicProfileHighlightedAchievement[]
  lastActivity: {
    recentCourses: PublicProfileRecentCourse[]
    lastComment: PublicProfileLastComment | null
  }
}
