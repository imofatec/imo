import CertificateButton from '@/components/watch/CertificateButton'
import LessonList from '@/components/watch/LessonList'
import type { WatchLesson } from '@/types/watch'

type Props = {
  lessons: WatchLesson[]
  watchedCount: number
  progressPercent: number
  currentLessonId: string
  onSelectLesson: (lessonId: string) => void
  watchedLessonIds: Set<string>
  onToggleLessonWatched: (lessonId: string) => void
  certificateDisabled?: boolean
}

export default function LessonTabContent({
  lessons,
  watchedCount,
  progressPercent,
  currentLessonId,
  onSelectLesson,
  watchedLessonIds,
  onToggleLessonWatched,
  certificateDisabled = true,
}: Props) {
  return (
    <aside className="xl:border-white/10 xl:border-l xl:pl-6">
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
        onToggleLessonWatched={onToggleLessonWatched}
      />
      <CertificateButton disabled={certificateDisabled} />
    </aside>
  )
}
