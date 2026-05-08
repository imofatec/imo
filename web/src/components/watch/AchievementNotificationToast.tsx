import { Trophy } from 'lucide-react'
import type { AchievementUnlockedNotification } from '@/types/notification'

type AchievementNotificationToastProps = {
  achievement: AchievementUnlockedNotification
}

export default function AchievementNotificationToast({
  achievement,
}: AchievementNotificationToastProps) {
  const imageSrc = achievement.imageUrl?.trim() || null

  return (
    <div className="border-cyan/45 shadow-cyan/10 flex w-86 max-w-[calc(100vw-24px)] items-center gap-3 rounded-lg border bg-[#171a21] p-3 shadow-2xl ring-1 ring-black/40">
      <div className="flex h-14 w-14 shrink-0 items-center justify-center overflow-hidden rounded-sm border border-white/10 bg-[#0b1118]">
        {imageSrc ? (
          <img src={imageSrc} alt={achievement.title} className="h-full w-full object-contain" />
        ) : (
          <Trophy size={30} className="text-cyan" />
        )}
      </div>

      <div className="min-w-0">
        <p className="text-cyan text-[11px] font-bold tracking-[0.16em] uppercase">
          Conquista desbloqueada
        </p>
        <p className="mt-1 truncate text-sm font-semibold text-white">{achievement.title}</p>
        <p className="mt-0.5 line-clamp-1 text-xs leading-5 text-white/70">
          {achievement.description}
        </p>
      </div>
    </div>
  )
}
