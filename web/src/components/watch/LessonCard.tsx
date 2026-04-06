import type { WatchLesson } from '@/types/watch'

type Props = {
  lesson: WatchLesson
  isActive: boolean
  onSelect: () => void
  isWatched: boolean
  onToggleWatched: () => void
}

export default function LessonCard({
  lesson,
  isActive,
  onSelect,
  isWatched,
  onToggleWatched,
}: Props) {
  return (
    <article className="w-full py-4 text-left">
      <div className="flex items-start justify-between gap-3">
        <button type="button" onClick={onSelect} className="min-w-0 flex-1 text-left">
          <p className="text-xs tracking-[0.16em] text-white/45 uppercase">Aula {lesson.order}</p>
          <p className={`mt-1 text-sm font-medium ${isActive ? 'text-cyan' : 'text-white'}`}>
            {lesson.title}
          </p>
          <p className="mt-2 text-xs text-white/55">{lesson.duration}</p>
        </button>

        <label
          className="mt-0.5 inline-flex cursor-pointer items-center gap-2 rounded-full border border-white/15 bg-white/5 px-2.5 py-1.5"
          aria-label={`Marcar aula ${lesson.order} como assistida`}
        >
          <input
            type="checkbox"
            checked={isWatched}
            onChange={onToggleWatched}
            className="accent-cyan h-4 w-4 cursor-pointer"
          />
          <span className={`text-xs font-medium ${isWatched ? 'text-cyan' : 'text-white/75'}`}>
            {isWatched ? 'Assistido' : 'Marcar'}
          </span>
        </label>
      </div>
    </article>
  )
}
