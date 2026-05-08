import { LoaderCircle } from 'lucide-react'

type Props = {
  disabled?: boolean
  loading?: boolean
  onClick?: () => void
}

export default function ShareProgressButton({ disabled = true, loading = false, onClick }: Props) {
  return (
    <button
      type="button"
      disabled={disabled || loading}
      onClick={onClick}
      className={`mt-5 flex w-full cursor-pointer items-center justify-center rounded-2xl border px-4 py-3 text-sm font-medium transition ${
        disabled || loading
          ? 'cursor-not-allowed border-cyan/30 bg-cyan/10 text-cyan/60'
          : 'border-cyan bg-cyan/20 text-cyan hover:bg-cyan/30'
      }`}
      aria-label="Compartilhar progresso"
    >
      <span className="flex items-center justify-center gap-2">
        {loading ? <LoaderCircle size={16} className="animate-spin" /> : null}
        {loading ? 'Preparando marco...' : 'Compartilhar progresso'}
      </span>
    </button>
  )
}
