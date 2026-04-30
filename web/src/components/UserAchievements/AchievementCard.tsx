import type { AchievementListItem } from '@/types/achievement'
import { Lock, Trophy } from 'lucide-react'

type AchievementCardProps = {
  achievement: AchievementListItem
}

export default function AchievementCard({ achievement }: AchievementCardProps) {
  const progress = Math.min(Math.max(achievement.progressPercentage ?? 0, 0), 100)

  return (
    <article className="group hover:border-cyan/30 relative overflow-visible rounded-3xl border border-white/10 bg-[#14082f] p-4 transition hover:-translate-y-1 hover:bg-[#1a0d3c]">
      <div className="relative flex aspect-square items-center justify-center overflow-hidden rounded-2xl border border-white/10 bg-[#0f0628] p-4">
        {achievement.imageSrc ? (
          <img
            src={achievement.imageSrc}
            alt={achievement.title}
            className={`h-full w-full object-contain transition ${achievement.isUnlocked ? '' : 'opacity-45 grayscale'}`}
          />
        ) : (
          <Trophy size={46} className={achievement.isUnlocked ? 'text-cyan' : 'text-white/35'} />
        )}

        <div className="absolute top-3 right-3 rounded-full border border-white/10 bg-black/45 px-2 py-1 text-[11px] font-semibold tracking-[0.2em] text-white/75 uppercase">
          {achievement.isUnlocked ? 'Desbloqueada' : 'Bloqueada'}
        </div>

        {!achievement.isUnlocked ? (
          <div className="absolute inset-0 flex items-center justify-center bg-black/25">
            <div className="flex h-12 w-12 items-center justify-center rounded-full border border-white/15 bg-black/50">
              <Lock size={20} className="text-white/80" />
            </div>
          </div>
        ) : null}
      </div>

      <div className="mt-4 space-y-2">
        <div className="flex items-center justify-between gap-3">
          <h3 className="line-clamp-1 text-sm font-semibold text-white">{achievement.title}</h3>
          <span className="shrink-0 text-xs text-white/45">{progress}%</span>
        </div>

        <div className="h-2 overflow-hidden rounded-full bg-white/8">
          <div
            className="bg-cyan h-full rounded-full transition-all"
            style={{ width: `${progress}%` }}
          />
        </div>

        <p className="text-xs text-white/55">
          {achievement.currentValue}/{achievement.targetValue || 0}
        </p>
      </div>

      <div className="pointer-events-none absolute inset-x-2 bottom-[calc(100%+0.75rem)] z-20 translate-y-2 rounded-2xl border border-white/10 bg-[#0a031d]/96 p-4 opacity-0 shadow-2xl transition duration-200 group-hover:translate-y-0 group-hover:opacity-100">
        <p className="text-sm font-semibold text-white">{achievement.title}</p>
        <p className="mt-2 text-sm leading-5 text-white/75">{achievement.description}</p>
        <p className="mt-3 text-xs text-white/55">
          {achievement.isUnlocked
            ? `Desbloqueada em ${achievement.unlockedLabel}`
            : achievement.unlockedLabel}
        </p>
      </div>
    </article>
  )
}
