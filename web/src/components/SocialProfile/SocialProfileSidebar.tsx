import { categoryOptions } from '@/constants/courseOptions'
import { getSocialProfileInitials, getSocialProfileOptionLabel } from '@/lib/socialProfile'
import type { AchievementListItem } from '@/types/achievement'
import { Trophy } from 'lucide-react'

type SocialProfileSidebarProps = {
  name: string
  profileImageSrc: string | null
  categories: string[]
  featuredAchievements: AchievementListItem[]
}

export default function SocialProfileSidebar({
  name,
  profileImageSrc,
  categories,
  featuredAchievements,
}: SocialProfileSidebarProps) {
  return (
    <aside className="space-y-5">
      <div className="rounded-[1.75rem] border border-white/10 bg-[#10052b] p-5">
        <div className="flex flex-col items-center text-center">
          <div className="border-cyan/30 shadow-[0_0_0_10px_rgba(34,211,238,0.06)] flex h-40 w-40 items-center justify-center overflow-hidden rounded-4xl border bg-[#0f0628] text-4xl font-semibold text-cyan">
            {profileImageSrc ? (
              <img src={profileImageSrc} alt={name} className="h-full w-full object-cover" />
            ) : (
              getSocialProfileInitials(name)
            )}
          </div>

          <div className="mt-5">
            <h2 className="text-2xl font-semibold text-white">{name}</h2>
          </div>

          <div className="mt-5 flex w-full flex-wrap justify-center gap-2">
            {categories.length ? (
              categories.map((category) => (
                <span
                  key={category}
                  className="rounded-full border border-cyan/20 bg-cyan/10 px-3 py-1 text-xs font-medium text-cyan"
                >
                  {getSocialProfileOptionLabel(category, categoryOptions, category)}
                </span>
              ))
            ) : (
              <span className="rounded-full border border-white/10 bg-white/5 px-3 py-1 text-xs text-white/55">
                Sem categorias em destaque
              </span>
            )}
          </div>
        </div>
      </div>

      <div className="rounded-[1.75rem] border border-white/10 bg-[#10052b] p-5">
        <div className="flex items-center gap-2 border-b border-white/10 pb-4">
          <Trophy size={18} className="text-cyan" />
          <h2 className="text-lg font-semibold text-white">Conquistas destacadas</h2>
        </div>

        <div className="mt-5 space-y-3">
          {featuredAchievements.map((achievement) => (
            <article
              key={achievement.key}
              className="rounded-2xl border border-white/10 bg-white/5 p-3"
            >
              <div className="flex items-start gap-3">
                <div className="flex h-14 w-14 shrink-0 items-center justify-center rounded-2xl border border-cyan/20 bg-[#0f0628]">
                  {achievement.imageSrc ? (
                    <img
                      src={achievement.imageSrc}
                      alt={achievement.title}
                      className="h-10 w-10 object-contain"
                    />
                  ) : (
                    <Trophy size={24} className="text-cyan" />
                  )}
                </div>

                <div className="min-w-0">
                  <p className="text-sm font-semibold text-white">{achievement.title}</p>
                  <p className="mt-1 text-xs leading-5 text-white/65">{achievement.description}</p>
                  <p className="mt-2 text-[11px] uppercase tracking-[0.22em] text-cyan/75">
                    {achievement.unlockedLabel}
                  </p>
                </div>
              </div>
            </article>
          ))}
        </div>
      </div>
    </aside>
  )
}
