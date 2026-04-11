import { Link } from 'react-router-dom'

type Props = {
  label: string
  to?: string
}

export default function CategoryChip({ label, to = '/categorias' }: Props) {
  return (
    <Link
      to={to}
      className="rounded-full border border-white/10 bg-white/5 px-4 py-2 text-sm text-white/90 transition hover:border-cyan/40 hover:bg-cyan/10 hover:text-cyan"
    >
      {label}
    </Link>
  )
}
