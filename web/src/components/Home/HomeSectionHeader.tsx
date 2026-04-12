import { ChevronRight } from 'lucide-react'
import { Link } from 'react-router-dom'

type Props = {
  title: string
  to?: string
  actionLabel?: string
}

export default function HomeSectionHeader({ title, to, actionLabel = 'Ver todos' }: Props) {
  return (
    <div className="mb-4 flex items-center justify-between">
      <h2 className="text-xl font-semibold text-white">{title}</h2>

      {to ? (
        <Link
          to={to}
          className="text-cyan inline-flex items-center gap-1 text-sm transition hover:text-cyan/80"
        >
          {actionLabel}
          <ChevronRight size={16} />
        </Link>
      ) : null}
    </div>
  )
}
