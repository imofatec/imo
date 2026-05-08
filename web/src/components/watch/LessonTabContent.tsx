import ShareProgressButton from '@/components/watch/ShareProgressButton'
import LessonList from '@/components/watch/LessonList'
import type { CourseDetailsLesson } from '@/types/course'

type Props = {
  lessons: CourseDetailsLesson[]
  watchedCount: number
  progressPercent: number
  currentLessonId: string
  onSelectLesson: (lessonId: string) => void
  watchedLessonIds: Set<string>
  markingLessonIds: Set<string>
  onToggleLessonWatched: (lessonId: string) => void
  onOpenShareProgress: () => void
  isPreparingMilestone: boolean
  shareDisabled?: boolean
}

export default function LessonTabContent({
  lessons,
  watchedCount,
  progressPercent,
  currentLessonId,
  onSelectLesson,
  watchedLessonIds,
  markingLessonIds,
  onToggleLessonWatched,
  onOpenShareProgress,
  isPreparingMilestone,
  shareDisabled = true,
}: Props) {
  return (
    <aside className="xl:border-l xl:border-white/10 xl:pl-6">
      <div className="mb-5 space-y-3">
        <h2 className="text-lg font-semibold text-white">Aulas do curso</h2>

        <div className="h-2 w-full overflow-hidden rounded-full bg-white/10">
          <div className="bg-cyan h-full rounded-full" style={{ width: `${progressPercent}%` }} />
        </div>

        <p className="text-xs text-white/60">
          {watchedCount} de {lessons.length} aulas concluídas
        </p>
      </div>

      <LessonList
        lessons={lessons}
        currentLessonId={currentLessonId}
        onSelectLesson={onSelectLesson}
        watchedLessonIds={watchedLessonIds}
        markingLessonIds={markingLessonIds}
        onToggleLessonWatched={onToggleLessonWatched}
      />
      <ShareProgressButton
        disabled={shareDisabled}
        loading={isPreparingMilestone}
        onClick={onOpenShareProgress}
      />
    </aside>
  )
}
