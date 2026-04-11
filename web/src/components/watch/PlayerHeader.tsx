import LessonNavigation from '@/components/watch/LessonNavigation'
import type { CourseDetailsLesson } from '@/types/course'

type PlayerHeaderProps = {
  lesson: CourseDetailsLesson | null
  onPreviousLesson: () => void
  onNextLesson: () => void
  hasPreviousLesson: boolean
  hasNextLesson: boolean
}

export default function PlayerHeader({
  lesson,
  onPreviousLesson,
  onNextLesson,
  hasPreviousLesson,
  hasNextLesson,
}: PlayerHeaderProps) {
  if (!lesson) {
    return (
      <article className="overflow-hidden rounded-2xl border border-white/10 bg-[#14082f]/90 shadow-sm">
        <div className="aspect-video w-full bg-black" />

        <div className="space-y-3 p-5">
          <h2 className="text-xl font-semibold text-white">Carregando aula...</h2>
        </div>
      </article>
    )
  }

  return (
    <article className="overflow-hidden rounded-2xl border border-white/10 bg-[#14082f]/90 shadow-sm">
      <div className="aspect-video w-full bg-black">
        <iframe
          className="h-full w-full"
          src={`https://www.youtube.com/embed/${lesson.youtubeLink}`}
          title={lesson.title}
          allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
          allowFullScreen
        />
      </div>

      <div className="space-y-3 p-5">
        <div className="flex flex-wrap items-center justify-between gap-3 text-xs text-white/70">
          <span className="bg-cyan/15 text-cyan rounded-full px-2.5 py-1 font-medium">
            Aula {lesson.indexInCourse}
          </span>

          <LessonNavigation
            onPreviousLesson={onPreviousLesson}
            onNextLesson={onNextLesson}
            hasPreviousLesson={hasPreviousLesson}
            hasNextLesson={hasNextLesson}
          />
        </div>

        <h2 className="text-xl font-semibold text-white">{lesson.title}</h2>
        <p className="text-sm leading-7 text-white/80">{lesson.description}</p>
      </div>
    </article>
  )
}
