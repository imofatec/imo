import { useEffect, useState } from 'react'
import { toast } from 'sonner'
import { useNavigate, useParams } from 'react-router-dom'
import LessonTabContent from '@/components/watch/LessonTabContent'
import LessonCommentsSection from '@/components/watch/LessonCommentsSection'
import PlayerHeader from '@/components/watch/PlayerHeader'
import ProgressMilestoneModal from '@/components/watch/ProgressMilestoneModal'
import WatchHeader from '@/components/watch/WatchHeader'
import { useAchievementNotificationStream } from '@/hooks/useAchievementNotificationStream'
import { useCurrentCourse } from '@/hooks/useCurrentCourse'
import { useCurrentProgress } from '@/hooks/useCurrentProgress'
import { showRequestErrorToast, useRequestErrorToast } from '@/lib/requestToast'
import { createProgressMilestoneRequest } from '@/services/progressMilestone/createProgressMilestone'
import type { CourseDetailsLesson } from '@/types/course'
import type { ProgressMilestone } from '@/types/progressMilestone'

export default function WatchPage() {
  useAchievementNotificationStream()

  const { courseId, idLesson } = useParams()
  const navigate = useNavigate()
  const { course, error: courseError } = useCurrentCourse(courseId ?? '')
  const {
    currentProgress,
    watchedLessonIds,
    markingLessonIds,
    markLessonAsWatched,
    error: progressError,
  } = useCurrentProgress(courseId ?? '')
  const [currentLesson, setCurrentLesson] = useState<CourseDetailsLesson | null>(null)
  const [currentLessonId, setCurrentLessonId] = useState('')
  const [isShareModalOpen, setIsShareModalOpen] = useState(false)
  const [milestone, setMilestone] = useState<ProgressMilestone | null>(null)
  const [milestoneCourseId, setMilestoneCourseId] = useState<string | null>(null)
  const [milestoneError, setMilestoneError] = useState<string | null>(null)
  const [isPreparingMilestone, setIsPreparingMilestone] = useState(false)
  const lessonsCount = course?.lessons.length ?? 0

  useRequestErrorToast(courseError, { id: 'watch-course-error' })
  useRequestErrorToast(progressError, { id: 'watch-progress-error' })

  useEffect(() => {
    if (!course?.lessons.length) {
      setCurrentLesson(null)
      return
    }

    const lessonFromUrl =
      course.lessons.find((lesson) => lesson.youtubeLink === idLesson) ?? course.lessons[0]

    setCurrentLesson(lessonFromUrl)
    setCurrentLessonId(lessonFromUrl.id)
  }, [course, idLesson])

  useEffect(() => {
    setIsShareModalOpen(false)
    setMilestone(null)
    setMilestoneCourseId(null)
    setMilestoneError(null)
    setIsPreparingMilestone(false)
  }, [courseId])

  const watchedCount = watchedLessonIds.size
  const progressPercent = currentProgress?.summary.completionPercentage ?? 0

  const allWatched = lessonsCount > 0 && watchedCount === lessonsCount
  const currentLessonIndex =
    course?.lessons.findIndex((lesson) => lesson.id === currentLessonId) ?? -1
  const hasPreviousLesson = currentLessonIndex > 0
  const hasNextLesson =
    currentLessonIndex >= 0 && currentLessonIndex < (course?.lessons.length ?? 0) - 1

  function handleSelectLesson(lessonId: string) {
    if (!courseId || !course?.lessons.length) return

    const lesson = course.lessons.find((item) => item.id === lessonId)

    if (!lesson) return

    setCurrentLesson(lesson)
    setCurrentLessonId(lesson.id)
    navigate(`/cursos/${courseId}/${lesson.youtubeLink}`)
  }

  function handlePreviousLesson() {
    if (!course?.lessons.length || currentLessonIndex <= 0) return

    handleSelectLesson(course.lessons[currentLessonIndex - 1].id)
  }

  function handleNextLesson() {
    if (!course?.lessons.length || currentLessonIndex < 0) return

    const nextLesson = course.lessons[currentLessonIndex + 1]

    if (!nextLesson) return

    handleSelectLesson(nextLesson.id)
  }

  async function handleToggleLessonWatched(lessonId: string) {
    await markLessonAsWatched(lessonId)
  }

  async function loadMilestone(forceRefresh = false) {
    if (!courseId || !allWatched || isPreparingMilestone) return

    if (!forceRefresh && milestone && milestoneCourseId === courseId) {
      setMilestoneError(null)
      return
    }

    setIsPreparingMilestone(true)
    setMilestoneError(null)

    try {
      const responseMilestone = await createProgressMilestoneRequest(courseId)

      setMilestone(responseMilestone)
      setMilestoneCourseId(courseId)
    } catch (requestError) {
      const message = showRequestErrorToast(
        requestError,
        'Não foi possível preparar o marco de progresso. Por favor, tente novamente.',
        { id: 'progress-milestone-error' }
      )

      setMilestoneError(message)
    } finally {
      setIsPreparingMilestone(false)
    }
  }

  async function handleOpenShareProgress() {
    if (!courseId || !allWatched) return

    setIsShareModalOpen(true)
    await loadMilestone()
  }

  async function handleRetryMilestone() {
    await loadMilestone(true)
  }

  function handleCloseShareModal() {
    setIsShareModalOpen(false)
  }

  function handleMilestoneReadyFeedback() {
    if (!milestone || isPreparingMilestone || milestoneError) return

    toast.success('Marco pronto para compartilhar', {
      id: 'progress-milestone-ready',
      description: 'Use a prévia pública para baixar a imagem ou compartilhar o link.',
    })
  }

  useEffect(() => {
    handleMilestoneReadyFeedback()
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [milestone, isPreparingMilestone, milestoneError])

  return (
    <main className="min-h-screen w-full bg-[#0C0424]">
      <WatchHeader courseTitle={course?.course.name.name ?? 'Carregando curso...'} />

      <section className="mx-auto w-full max-w-360 px-4 py-8 sm:px-6 lg:px-8">
        <div className="grid gap-6 xl:grid-cols-[minmax(0,1.6fr)_minmax(320px,1fr)] xl:gap-0">
          <div className="space-y-6 xl:pr-6">
            <PlayerHeader
              lesson={currentLesson}
              onPreviousLesson={handlePreviousLesson}
              onNextLesson={handleNextLesson}
              hasPreviousLesson={hasPreviousLesson}
              hasNextLesson={hasNextLesson}
            />
            <LessonCommentsSection lessonId={currentLesson?.id} />
          </div>

          <LessonTabContent
            lessons={course?.lessons ?? []}
            watchedCount={watchedCount}
            progressPercent={progressPercent}
            currentLessonId={currentLessonId}
            onSelectLesson={handleSelectLesson}
            watchedLessonIds={watchedLessonIds}
            markingLessonIds={markingLessonIds}
            onToggleLessonWatched={handleToggleLessonWatched}
            onOpenShareProgress={handleOpenShareProgress}
            isPreparingMilestone={isPreparingMilestone}
            shareDisabled={!allWatched}
          />
        </div>
      </section>

      <ProgressMilestoneModal
        isOpen={isShareModalOpen}
        milestone={milestone}
        loading={isPreparingMilestone}
        error={milestoneError}
        onClose={handleCloseShareModal}
        onRetry={handleRetryMilestone}
      />
    </main>
  )
}
