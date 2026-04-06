import LessonCard from '@/components/watch/LessonCard'
import type { WatchLesson } from '@/types/watch'

type Props = {
  lessons: WatchLesson[]
  currentLessonId: string
  onSelectLesson: (lessonId: string) => void
  watchedLessonIds: Set<string>
  onToggleLessonWatched: (lessonId: string) => void
}

export default function LessonList({
  lessons,
  currentLessonId,
  onSelectLesson,
  watchedLessonIds,
  onToggleLessonWatched,
}: Props) {
  return (
    <ul className="divide-y divide-white/10 border-y border-white/10">
      {lessons.map((lesson) => (
        <li key={lesson.id}>
          <LessonCard
            lesson={lesson}
            isActive={lesson.id === currentLessonId}
            onSelect={() => onSelectLesson(lesson.id)}
            isWatched={watchedLessonIds.has(lesson.id)}
            onToggleWatched={() => onToggleLessonWatched(lesson.id)}
          />
        </li>
      ))}
    </ul>
  )
}
