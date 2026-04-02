import Linkbutton from '../ui/LinkButton'

type Course = {
  id: string
  title: string
  image: string
  description: string
  category: string
}

type Props = {
  course: Course | null
  onClose: () => void
  actionLabel?: string
}

export default function CourseModal({ course, onClose, actionLabel = 'Inscrever-se' }: Props) {
  if (!course) return null

  return (
    <div
      onClick={onClose}
      className="fixed inset-0 z-50 flex items-center justify-center bg-black/60 px-4"
    >
      <div
        onClick={(e) => e.stopPropagation()}
        className="w-full max-w-2xl rounded-2xl border border-white/10 bg-[#14082f] p-6 shadow-2xl"
      >
        <div className="flex items-start justify-between gap-4">
          <div>
            <span className="bg-cyan/20 text-cyan inline-block rounded-full px-3 py-1 text-xs font-medium">
              {course.category}
            </span>

            <h2 className="mt-3 text-2xl font-bold text-white">{course.title}</h2>
          </div>

          <button
            onClick={onClose}
            className="rounded-lg border border-white/10 bg-white/5 px-3 py-2 text-sm text-white hover:bg-white/10"
          >
            Fechar
          </button>
        </div>

        <img
          src={course.image}
          alt={course.title}
          className="mt-5 h-56 w-full rounded-xl object-cover"
        />

        <div className="mt-5 space-y-4">
          <p className="text-sm leading-7 text-white">{course.description}</p>

          <div className="grid gap-3 sm:grid-cols-3">
            <div className="rounded-xl p-4">
              <p className="text-sm text-white">Nível:</p>
              <p className="mt-1 text-sm font-medium text-white">Iniciante</p>
            </div>

            <div className="rounded-xl p-4">
              <p className="text-sm text-white">Duração:</p>
              <p className="mt-1 text-sm font-medium text-white">8 horas</p>
            </div>

            <div className="rounded-xl p-4">
              <p className="text-sm text-white">Aulas:</p>
              <p className="mt-1 text-sm font-medium text-white">24 aulas</p>
            </div>
          </div>
          <Linkbutton to={'/mycourses'} variant="cyanOutline" className="text-cyan">
            {actionLabel}
          </Linkbutton>
        </div>
      </div>
    </div>
  )
}
