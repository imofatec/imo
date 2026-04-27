import type { ReactNode } from 'react'

type SocialProfileInfoCardProps = {
  icon: ReactNode
  label: string
  value: string
}

type SocialProfileInfoSectionProps = {
  items: SocialProfileInfoCardProps[]
}

function SocialProfileInfoCard({ icon, label, value }: SocialProfileInfoCardProps) {
  return (
    <div className="rounded-2xl border border-white/10 bg-[#10052b] p-4">
      <div className="flex items-center gap-3">
        <div className="flex h-11 w-11 items-center justify-center rounded-2xl border border-cyan/20 bg-cyan/10 text-cyan">
          {icon}
        </div>

        <div>
          <p className="text-xs uppercase tracking-[0.24em] text-white/45">{label}</p>
          <p className="mt-1 text-sm font-medium text-white/90">{value}</p>
        </div>
      </div>
    </div>
  )
}

export default function SocialProfileInfoSection({ items }: SocialProfileInfoSectionProps) {
  return (
    <section className="rounded-[1.75rem] border border-white/10 bg-[#10052b] p-6">
      <div className="mb-5 border-b border-white/10 pb-4">
        <h2 className="text-lg font-semibold text-white">Informações</h2>
      </div>

      <div className="grid gap-4 md:grid-cols-2">
        {items.map((item) => (
          <SocialProfileInfoCard
            key={item.label}
            icon={item.icon}
            label={item.label}
            value={item.value}
          />
        ))}
      </div>
    </section>
  )
}
