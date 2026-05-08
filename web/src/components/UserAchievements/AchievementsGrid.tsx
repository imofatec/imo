import AchievementCard from '@/components/UserAchievements/AchievementCard'
import AchievementCardSkeleton from '@/components/UserAchievements/AchievementCardSkeleton'
import type { AchievementListItem } from '@/types/achievement'
import { Trophy } from 'lucide-react'

type AchievementsGridProps = {
  items: AchievementListItem[]
  isLoading: boolean
}

const SKELETON_ITEMS = 8

export default function AchievementsGrid({ items, isLoading }: AchievementsGridProps) {
  if (isLoading) {
    return (
      <div className="grid grid-cols-2 gap-4 md:grid-cols-3 lg:grid-cols-4">
        {Array.from({ length: SKELETON_ITEMS }).map((_, index) => (
          <AchievementCardSkeleton key={index} />
        ))}
      </div>
    )
  }

  if (!items.length) {
    return (
      <div className="rounded-2xl border border-dashed border-white/10 bg-white/3 p-8 text-center">
        <Trophy size={40} className="mx-auto text-white/35" />
        <p className="mt-4 text-lg font-semibold text-white">Nenhuma conquista encontrada</p>
        <p className="mt-2 text-sm text-white/60">
          Quando suas conquistas estiverem disponiveis, elas vao aparecer aqui.
        </p>
      </div>
    )
  }

  return (
    <div className="grid grid-cols-2 gap-4 md:grid-cols-3 lg:grid-cols-4">
      {items.map((achievement) => (
        <AchievementCard key={achievement.key} achievement={achievement} />
      ))}
    </div>
  )
}
