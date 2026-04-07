import { ChevronLeft, ChevronRight } from 'lucide-react'

type Props = {
  onPreviousLesson: () => void
  onNextLesson: () => void
  hasPreviousLesson: boolean
  hasNextLesson: boolean
}

export default function LessonNavigation({
  onPreviousLesson,
  onNextLesson,
  hasPreviousLesson,
  hasNextLesson,
}: Props) {
  return (
    <div className="flex items-center gap-2">
      <button
        type="button"
        onClick={onPreviousLesson}
        disabled={!hasPreviousLesson}
        className="inline-flex items-center gap-1 rounded-lg border border-white/15 bg-white/5 px-3 py-2 text-xs text-white transition hover:bg-white/10 disabled:cursor-not-allowed disabled:opacity-40"
      >
        <ChevronLeft size={16} />
        Anterior
      </button>

      <button
        type="button"
        onClick={onNextLesson}
        disabled={!hasNextLesson}
        className="inline-flex items-center gap-1 rounded-lg border border-white/15 bg-white/5 px-3 py-2 text-xs text-white transition hover:bg-white/10 disabled:cursor-not-allowed disabled:opacity-40"
      >
        Proxima
        <ChevronRight size={16} />
      </button>
    </div>
  )
}
