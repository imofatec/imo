import CourseCard from '@/components/AllCourses/CourseCard'
import SkeletonCourseCard from '@/components/AllCourses/SkeletonCourseCard'
import type { Course } from '@/types/course'
import type { UserProgress } from '@/types/userProgress'

type BaseProps = {
  loading: boolean
  error: string | null
  pageSize: number
}

type CoursesProps = BaseProps & {
  mode: 'courses'
  items: Course[]
}

type ProgressProps = BaseProps & {
  mode: 'progress'
  items: UserProgress[]
}

type Props = CoursesProps | ProgressProps

export default function MyCoursesContent({
  items,
  loading,
  error,
  pageSize,
  mode,
}: Props) {
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

  if (items.length === 0) {
    return (
      <div className="flex h-80 items-center justify-center rounded-2xl border border-white/10 bg-white/5">
        <p className="text-center text-sm text-white/70">Nenhum curso encontrado nesse filtro.</p>
      </div>
    )
  }

  if (mode === 'courses') {
    return (
      <div className="grid grid-cols-1 gap-6 sm:grid-cols-2 md:grid-cols-3 xl:grid-cols-5">
        {items.map((course) => (
          <CourseCard
            key={course.id}
            course={course}
            actionLabel="Retomar curso"
            modalActionLabel="Retomar curso"
          />
        ))}
      </div>
    )
  }

  return (
    <div className="grid grid-cols-1 gap-6 sm:grid-cols-2 md:grid-cols-3 xl:grid-cols-5">
      {items.map((item) => (
        <CourseCard
          key={item.progress.id}
          course={item.course}
          actionLabel="Retomar curso"
          modalActionLabel="Retomar curso"
        />
      ))}
    </div>
  )
}
