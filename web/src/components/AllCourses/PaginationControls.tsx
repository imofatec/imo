import { ChevronRight, ChevronLeft } from 'lucide-react'

type Props = {
  page: number
  totalPages?: number
  isPrevDisabled: boolean
  isNextDisabled: boolean
  onPrev?: () => void
  onNext?: () => void
  onPageChange?: (page: number) => void
}

export default function PaginationControls({
  page,
  totalPages = 5,
  isPrevDisabled,
  isNextDisabled,
  onPrev,
  onNext,
  onPageChange,
}: Props) {
  const pages = Array.from({ length: totalPages }, (_, i) => i + 1)

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
        {pages.slice(0, 3).map((pageNumber) => {
          const isActive = pageNumber === page

          return (
            <button
              key={pageNumber}
              onClick={() => onPageChange?.(pageNumber)}
              className={`relative text-lg font-semibold transition ${
                isActive ? 'text-cyan' : 'hover:text-cyan text-white/80'
              }`}
            >
              {pageNumber}
              {isActive && (
                <span className="bg-cyan absolute -bottom-2 left-1/2 h-0.5 w-5 -translate-x-1/2 rounded-full" />
              )}
            </button>
          )
        })}

        {totalPages > 4 && (
          <>
            <span className="text-lg font-semibold text-white/50">...</span>

            <button
              onClick={() => onPageChange?.(totalPages)}
              className={`relative text-lg font-semibold transition ${
                page === totalPages ? 'text-cyan' : 'hover:text-cyan text-white/80'
              }`}
            >
              {totalPages}
              {page === totalPages && (
                <span className="bg-cyan absolute -bottom-2 left-1/2 h-0.5 w-5 -translate-x-1/2 rounded-full" />
              )}
            </button>
          </>
        )}
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
