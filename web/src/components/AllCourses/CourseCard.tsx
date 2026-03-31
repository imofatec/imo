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
}

export default function CourseCard({ course, onClick }: Props) {
  return (
    <div
      onClick={onClick}
      className="group cursor-pointer overflow-hidden rounded-2xl border border-white/10 bg-white/5 text-left shadow-sm transition hover:-translate-y-1 hover:bg-white/10"
    >
      <img src={course.image} alt={course.title} className="h-44 w-full object-cover" />

      <div className="p-4">
        <span className="bg-cyan/20 text-cyan inline-block rounded-full px-3 py-1 text-xs font-medium">
          {course.category}
        </span>

        <h2 className="mt-3 text-lg font-semibold text-white">{course.title}</h2>

        <p className="mt-3 text-sm leading-6 text-white">{course.description}</p>

        <Button variant="cyanOutline" className="group-hover:bg-cyan/20 text-cyan">
          Ver mais
        </Button>
      </div>
    </div>
  )
}
