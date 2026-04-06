import { ChevronRight, ChevronLeft } from 'lucide-react'

type Props = {
  page: number
  isPrevDisabled: boolean
  isNextDisabled: boolean
  onPrev?: () => void
  onNext?: () => void
}

export default function PaginationControls({
  page,
  isPrevDisabled,
  isNextDisabled,
  onPrev,
  onNext,
}: Props) {
  return (
    <div className="mt-10 flex items-center justify-center gap-4">
      <button
        onClick={onPrev}
        disabled={isPrevDisabled}
        className="border-cyan/50 text-cyan hover:bg-cyan/10 flex h-12 w-12 items-center justify-center rounded-full border transition disabled:cursor-not-allowed disabled:opacity-40"
      >
        <ChevronLeft size={24} />
      </button>

      <div className="flex items-center gap-5">
        <span className="relative text-lg font-semibold text-cyan">
          {page}
          <span className="bg-cyan absolute -bottom-2 left-1/2 h-0.5 w-5 -translate-x-1/2 rounded-full" />
        </span>
      </div>

      <button
        onClick={onNext}
        disabled={isNextDisabled}
        className="border-cyan/50 text-cyan hover:bg-cyan/10 flex h-12 w-12 items-center justify-center rounded-full border transition disabled:cursor-not-allowed disabled:opacity-40"
      >
        <ChevronRight size={24} />
      </button>
    </div>
  )
}
