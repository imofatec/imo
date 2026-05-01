import { categoryOptions } from '@/constants/courseOptions'
import { getSocialProfileInitials, getSocialProfileOptionLabel } from '@/lib/socialProfile'

type SocialProfileSidebarProps = {
  name: string
  profileImageSrc: string | null
  categories: string[]
}

export default function SocialProfileSidebar({
  name,
  profileImageSrc,
  categories,
}: SocialProfileSidebarProps) {
  return (
    <aside className="h-full">
      <div className="h-full rounded-[1.75rem] border border-white/10 bg-[#10052b] p-5">
        <div className="flex h-full flex-col items-center justify-center text-center">
          <div className="border-cyan/30 shadow-[0_0_0_10px_rgba(34,211,238,0.06)] flex h-40 w-40 items-center justify-center overflow-hidden rounded-4xl border bg-[#0f0628] text-4xl font-semibold text-cyan">
            {profileImageSrc ? (
              <img src={profileImageSrc} alt={name} className="h-full w-full object-cover" />
            ) : (
              getSocialProfileInitials(name)
            )}
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
                Categorias não adicionadas
              </span>
            )}
          </div>
        </div>
      </div>
    </aside>
  )
}
