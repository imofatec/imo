import CourseCard from '@/components/AllCourses/CourseCard'
import SkeletonCourseCard from '@/components/AllCourses/SkeletonCourseCard'
import type { Course } from '@/types/course'

type Props = {
  courses: Course[]
  loading: boolean
  error: string | null
  pageSize: number
}

export default function CoursesContent({ courses, loading, error, pageSize }: Props) {
  if (loading) {
    return (
      <div className="grid grid-cols-1 gap-6 sm:grid-cols-2 md:grid-cols-3 xl:grid-cols-5">
        {Array.from({ length: pageSize }).map((_, index) => (
          <SkeletonCourseCard key={index} />
        ))}
      </div>
    )
  }

  if (error) {
    return (
      <div className="rounded-2xl border border-red-400/30 bg-red-500/10 p-6 text-center text-sm text-red-300">
        Ocorreu um erro ao carregar os cursos.
      </div>
    )
  }

  if (courses.length === 0) {
    return (
      <div className="flex h-80 items-center justify-center rounded-2xl border border-white/10 bg-white/5">
        <p className="text-center text-sm text-white/70">
          Nenhum curso encontrado nessa categoria.
        </p>
      </div>
    )
  }

  return (
    <div className="grid grid-cols-1 gap-6 sm:grid-cols-2 md:grid-cols-3 xl:grid-cols-5">
      {courses.map((course) => (
        <CourseCard key={course.id} course={course} />
      ))}
    </div>
  )
}
