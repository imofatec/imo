import { formatSocialProfileDateTime } from '@/lib/socialProfile'
import type { PublicProfileSharedProgressMilestone } from '@/types/publicUserProfile'
import { ArrowUpRight, Clock3 } from 'lucide-react'

type SocialProfileMilestonesSectionProps = {
  milestones: PublicProfileSharedProgressMilestone[]
}

type MilestoneCardProps = {
  milestone: PublicProfileSharedProgressMilestone
}

function MilestoneCard({ milestone }: MilestoneCardProps) {
  return (
    <article className="space-y-3">
      <div className="relative overflow-hidden rounded-[1.9rem] border border-white/10 bg-[#0b031d] transition group-hover:border-cyan/30">
        <img
          src={milestone.imageUrl}
          alt={`Marco compartilhado do curso ${milestone.courseName}`}
          className="block aspect-[1200/630] w-full object-cover"
        />

        <div className="pointer-events-none absolute inset-x-0 bottom-0 h-28 bg-gradient-to-t from-[#0b031d] to-transparent" />
      </div>

      <div className="flex flex-col gap-3 px-1 sm:flex-row sm:items-center sm:justify-between">
        <div className="flex items-center gap-2 text-sm text-white/65">
          <Clock3 size={14} className="text-cyan/80" />
          <span>{formatSocialProfileDateTime(milestone.generatedAt, 'Data não encontrada')}</span>
        </div>

        <a
          href={milestone.shareUrl}
          target="_blank"
          rel="noreferrer"
          className="text-cyan inline-flex w-fit cursor-pointer items-center gap-2 rounded-full border border-cyan/30 bg-cyan/10 px-4 py-2 text-sm font-medium transition hover:bg-cyan/20"
        >
          Ver marco
          <ArrowUpRight size={16} />
        </a>
      </div>
    </article>
  )
}

export default function SocialProfileMilestonesSection({
  milestones,
}: SocialProfileMilestonesSectionProps) {
  const latestMilestone = milestones[0]

  return (
    <section>
      <div className="mb-5">
        <h2 className="text-lg font-semibold text-white">Marcos compartilhados</h2>
        <p className="mt-2 max-w-3xl text-sm leading-6 text-white/60">
          O marco de progresso mais recente que este usuário escolheu compartilhar.
        </p>
      </div>

      {latestMilestone ? (
        <div className="max-w-[960px]">
          <MilestoneCard milestone={latestMilestone} />
        </div>
      ) : (
        <p className="rounded-2xl border border-white/10 bg-white/5 px-4 py-3 text-sm text-white/60">
          Nenhum marco compartilhado ainda.
        </p>
      )}
    </section>
  )
}
