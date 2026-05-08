import type { CourseDetailsLesson } from '@/types/course'

type Props = {
  lesson: CourseDetailsLesson
  isActive: boolean
  onSelect: () => void
  isWatched: boolean
  isMarking: boolean
  onToggleWatched: () => void
}

export default function LessonCard({
  lesson,
  isActive,
  onSelect,
  isWatched,
  isMarking,
  onToggleWatched,
}: Props) {
  return (
    <article className="w-full py-4 text-left">
      <div className="flex items-start justify-between gap-3">
        <button type="button" onClick={onSelect} className="min-w-0 flex-1 text-left">
          <p className="text-xs tracking-[0.16em] text-white/45 uppercase">
            Aula {lesson.indexInCourse}
          </p>
          <p
            className={`mt-1 text-sm font-medium wrap-break-word ${isActive ? 'text-cyan' : 'text-white'}`}
          >
            {lesson.title}
          </p>
        </button>

        <label
          className={`mt-0.5 inline-flex items-center gap-2 rounded-full border px-2.5 py-1.5 ${
            isWatched ? 'border-cyan/40 bg-cyan/10' : 'border-white/15 bg-white/5'
          } ${isWatched || isMarking ? 'cursor-not-allowed opacity-90' : 'cursor-pointer'}`}
          aria-label={`Marcar aula ${lesson.indexInCourse} como assistida`}
        >
          <input
            type="checkbox"
            checked={isWatched}
            onChange={onToggleWatched}
            disabled={isWatched || isMarking}
            className="accent-cyan h-4 w-4 cursor-pointer disabled:cursor-not-allowed"
          />
          <span className={`text-xs font-medium ${isWatched ? 'text-cyan' : 'text-white/75'}`}>
            {isWatched ? 'Assistido' : 'Marcar'}
          </span>
        </label>
      </div>
    </article>
  )
}
