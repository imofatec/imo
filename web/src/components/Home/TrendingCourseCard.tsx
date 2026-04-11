import LinkButton from '@/components/ui/LinkButton'

type Props = {
  title: string
  author: string
  imageUrl: string
  to?: string
}

export default function TrendingCourseCard({
  title,
  author,
  imageUrl,
  to = '/categorias',
}: Props) {
  return (
    <article className="w-72 shrink-0 overflow-hidden rounded-2xl border border-white/10 bg-[#14082f]">
      <img src={imageUrl} alt={title} className="h-40 w-full object-cover" />

      <div className="space-y-2 p-4">
        <h3 className="line-clamp-2 text-base font-semibold text-white">{title}</h3>
        <p className="text-xs text-white/60">{author}</p>

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
