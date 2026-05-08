export type Achievement = {
  trigger: string
  key: string
  title: string
  description: string
  imageUrl: string
  unlockedAt: string | null
  currentValue: number
  targetValue: number
  progressPercentage: number
}

export type AchievementListItem = Achievement & {
  unlockedLabel: string
  isUnlocked: boolean
}

export type UserAchievementsResponse = {
  total: number
  unlockedCount: number
  lockedCount: number
  items: Achievement[]
}
