import LinkButton from '@/components/ui/LinkButton'

type ProfileCourseCardProps = {
  title: string
  subtitle: string
  imageUrl: string
  to: string
}

export default function ProfileCourseCard({ title, subtitle, imageUrl, to }: ProfileCourseCardProps) {
  return (
    <article className="group overflow-hidden rounded-2xl border border-white/10 bg-[#14082f] transition hover:-translate-y-0.5 hover:border-cyan/30">
      <img src={imageUrl} alt={title} className="h-36 w-full object-cover" />

      <div className="min-w-0 space-y-2 p-4">
        <h3 className="truncate text-base font-semibold text-white">{title}</h3>
        <p className="truncate text-xs text-white/60">{subtitle}</p>

        <LinkButton
          to={to}
          variant="cyanOutline"
          className="text-cyan mt-2! w-full border border-cyan/30 py-2"
        >
          Ver curso
        </LinkButton>
      </div>
    </article>
  )
}
