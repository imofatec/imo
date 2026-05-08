import { Link } from 'react-router-dom'
import { X } from 'lucide-react'
import CourseSkillChips from './CourseSkillChips'
import type { Course } from '@/types/course'

type Props = {
  course: Course | null
  onClose: () => void
  actionLabel?: string
  showEditButton?: boolean
  editTo?: string
  editLabel?: string
}

export default function CourseModal({
  course,
  onClose,
  actionLabel = 'Assistir primeira aula',
  showEditButton = false,
  editTo,
  editLabel = 'Editar curso',
}: Props) {
  if (!course) return null

  const videoId = course.firstLessonYoutubeLink
  const thumbnailUrl = `https://img.youtube.com/vi/${videoId}/maxresdefault.jpg`
  const actionClassName =
    'inline-flex w-full items-center justify-center rounded-xl border border-cyan bg-cyan/10 px-4 py-3 text-center text-sm font-medium text-cyan transition-transform duration-200 hover:bg-cyan/20 focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-cyan/40'
  const editButtonClassName =
    'inline-flex w-full items-center justify-center rounded-xl border border-white/20 bg-white/5 px-4 py-3 text-center text-sm font-medium text-white transition-transform duration-200 hover:bg-white/10 focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-white/30'
  const resolvedEditTo = editTo ?? `/editar-curso/${course.id}`

  return (
    <div
      onClick={onClose}
      className="fixed inset-0 z-50 flex items-center justify-center bg-black/60 px-4 py-6"
    >
      <div
        onClick={(event) => event.stopPropagation()}
        className="max-h-[90vh] w-full max-w-2xl overflow-y-auto rounded-2xl border border-white/10 bg-[#14082f] p-6 shadow-2xl"
      >
        <div className="flex items-start justify-between gap-4">
          <div className="min-w-0 flex-1">
            <span className="bg-cyan/20 text-cyan inline-block rounded-full px-3 py-1 text-xs font-medium">
              {course.category.name}
            </span>

            <h2 className="mt-3 text-2xl font-bold wrap-break-word text-white">
              {course.name.name}
            </h2>
          </div>

          <button
            onClick={onClose}
            type="button"
            className="shrink-0 self-start rounded-lg border border-white/10 bg-white/5 p-2 text-white/80 transition hover:bg-white/10 hover:text-white"
          >
            <X size={18} />
          </button>
        </div>

        <img
          src={thumbnailUrl}
          alt={course.name.name}
          className="mt-5 h-56 w-full rounded-xl object-cover"
        />

        <div className="mt-5 space-y-4">
          <p className="text-sm leading-7 wrap-break-word text-white">{course.description}</p>

          <CourseSkillChips categorySlug={course.category.slug} skillIds={course.skillIds} />

          <div className="grid gap-3 sm:grid-cols-3">
            <div className="rounded-xl bg-white/5 p-4">
              <p className="text-sm text-white/70">Nível</p>
              <p className="mt-1 text-sm font-medium text-white">{course.level.name}</p>
            </div>

            <div className="rounded-xl bg-white/5 p-4">
              <p className="text-sm text-white/70">Aulas</p>
              <p className="mt-1 text-sm font-medium text-white">
                {course.lessonsCount} aula{course.lessonsCount === 1 ? '' : 's'}
              </p>
            </div>

            <div className="rounded-xl bg-white/5 p-4">
              <p className="text-sm text-white/70">Status</p>
              <p className="mt-1 text-sm font-medium text-white">
                {course.isActive ? 'Ativo' : 'Inativo'}
              </p>
            </div>
          </div>
          {showEditButton ? (
            <div className="grid gap-3 sm:grid-cols-2">
              <Link
                to={`/cursos/${course.id}/${course.firstLessonYoutubeLink}`}
                onClick={onClose}
                className={actionClassName}
              >
                {actionLabel}
              </Link>

              <Link to={resolvedEditTo} onClick={onClose} className={editButtonClassName}>
                {editLabel}
              </Link>
            </div>
          ) : (
            <Link
              to={`/cursos/${course.id}/${course.firstLessonYoutubeLink}`}
              onClick={onClose}
              className={actionClassName}
            >
              {actionLabel}
            </Link>
          )}
        </div>
      </div>
    </div>
  )
}
