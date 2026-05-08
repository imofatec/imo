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

export type PublicProfileSharedProgressMilestone = {
  publicCode: string
  courseName: string
  generatedAt: string | null
  shareUrl: string
  imageUrl: string
}

export type PublicUserProfile = {
  name: string
  bio: string | null
  profilePicturePath: string | null
  categoriesOfInterest: string[] | null
  highlightedAchievements: PublicProfileHighlightedAchievement[]
  sharedProgressMilestones: PublicProfileSharedProgressMilestone[]
  lastActivity: {
    recentCourses: PublicProfileRecentCourse[]
    lastComment: PublicProfileLastComment | null
  }
}
