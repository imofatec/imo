import { ChevronRight, ChevronLeft, ChevronsLeft, ChevronsRight } from 'lucide-react'

type Props = {
  page: number
  totalPages?: number
  isPrevDisabled: boolean
  isNextDisabled: boolean
  onPrev?: () => void
  onNext?: () => void
  onPageChange?: (page: number) => void
}

type PageItem = number | 'ellipsis'

function buildPageItems(currentPage: number, totalPages: number): PageItem[] {
  if (totalPages <= 7) {
    return Array.from({ length: totalPages }, (_, index) => index + 1)
  }

  if (currentPage <= 3) {
    return [1, 2, 3, 'ellipsis', totalPages]
  }

  if (currentPage >= totalPages - 2) {
    return [1, 'ellipsis', totalPages - 2, totalPages - 1, totalPages]
  }

  return [1, 'ellipsis', currentPage - 1, currentPage, currentPage + 1, 'ellipsis', totalPages]
}

export default function PaginationControls({
  page,
  totalPages = 1,
  isPrevDisabled,
  isNextDisabled,
  onPrev,
  onNext,
  onPageChange,
}: Props) {
  const safeTotalPages = Math.max(1, totalPages)
  const safeCurrentPage = Math.min(Math.max(page, 1), safeTotalPages)
  const pageItems = buildPageItems(safeCurrentPage, safeTotalPages)

  return (
    <div className="mt-10 flex items-center justify-center gap-5">
      <div className="flex items-center gap-2">
        <button
          type="button"
          onClick={() => onPageChange?.(1)}
          disabled={safeCurrentPage === 1 || !onPageChange}
          className="border-cyan/40 text-cyan hover:bg-cyan/10 flex h-9 w-9 items-center justify-center rounded-full border text-sm transition disabled:cursor-not-allowed disabled:opacity-40"
        >
          <ChevronsLeft size={16} />
        </button>

        <button
          type="button"
          onClick={onPrev}
          disabled={isPrevDisabled}
          className="border-cyan/50 text-cyan hover:bg-cyan/10 flex h-12 w-12 items-center justify-center rounded-full border transition disabled:cursor-not-allowed disabled:opacity-40"
        >
          <ChevronLeft size={24} />
        </button>
      </div>

      <div className="flex items-center gap-4 text-lg font-semibold">
        {pageItems.map((item, index) =>
          item === 'ellipsis' ? (
            <span key={`ellipsis-${index}`} className="text-white/60">
              ...
            </span>
          ) : (
            <button
              key={item}
              type="button"
              onClick={() => onPageChange?.(item)}
              disabled={item === safeCurrentPage}
              className={`relative transition ${
                item === safeCurrentPage
                  ? 'text-cyan cursor-default'
                  : 'hover:text-cyan text-white/85'
              }`}
            >
              {item}
              {item === safeCurrentPage ? (
                <span className="bg-cyan absolute -bottom-2 left-1/2 h-0.5 w-5 -translate-x-1/2 rounded-full" />
              ) : null}
            </button>
          )
        )}
      </div>

      <div className="flex items-center gap-2">
        <button
          type="button"
          onClick={onNext}
          disabled={isNextDisabled}
          className="border-cyan/50 text-cyan hover:bg-cyan/10 flex h-12 w-12 items-center justify-center rounded-full border transition disabled:cursor-not-allowed disabled:opacity-40"
        >
          <ChevronRight size={24} />
        </button>

        <button
          type="button"
          onClick={() => onPageChange?.(safeTotalPages)}
          disabled={safeCurrentPage === safeTotalPages || !onPageChange}
          className="border-cyan/40 text-cyan hover:bg-cyan/10 flex h-9 w-9 items-center justify-center rounded-full border text-sm transition disabled:cursor-not-allowed disabled:opacity-40"
        >
          <ChevronsRight size={16} />
        </button>
      </div>
    </div>
  )
}
