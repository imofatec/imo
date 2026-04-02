import Button from '../ui/Button'

type Course = {
  id: string
  title: string
  image: string
  description: string
  category: string
}

type Props = {
  course: Course
  onClick: () => void
  actionLabel?: string
}

export default function CourseCard({ course, onClick, actionLabel = 'Ver mais' }: Props) {
  return (
    <div
      onClick={onClick}
      className="group flex h-full cursor-pointer flex-col overflow-hidden rounded-2xl border border-white/10 bg-white/5 text-left shadow-sm transition hover:-translate-y-1 hover:bg-white/10"
    >
      <img src={course.image} alt={course.title} className="h-44 w-full object-cover" />

      <div className="flex flex-1 flex-col p-4">
        <span className="bg-cyan/20 text-cyan inline-block self-start rounded-full px-3 py-1 text-xs font-medium">
          {course.category}
        </span>

        <h2 className="mt-3 text-lg font-semibold text-white">{course.title}</h2>

        <p className="mt-3 mb-4 [display:-webkit-box] h-6 overflow-hidden text-sm leading-6 text-white [-webkit-box-orient:vertical] [-webkit-line-clamp:1]">
          {course.description}
        </p>

        <Button variant="cyanOutline" className="text-cyan group-hover:bg-cyan/20 mt-auto!">
          {actionLabel}
        </Button>
      </div>
    </div>
  )
}
