import { useEffect, useMemo, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import authAxiosInstance from '@/api/authAxiosInstance'
import LessonTabContent from '@/components/watch/LessonTabContent'
import LessonCommentsSection from '@/components/watch/LessonCommentsSection'
import PlayerHeader from '@/components/watch/PlayerHeader'
import WatchHeader from '@/components/watch/WatchHeader'
import { useCurrentCourse } from '@/hooks/useCurrentCourse'
import { useCurrentProgress } from '@/hooks/useCurrentProgress'
import { useRequestErrorToast } from '@/lib/requestToast'
import type { CourseDetailsLesson } from '@/types/course'

export default function WatchPage() {
  const { courseId, idLesson } = useParams()
  const navigate = useNavigate()
  const { course, error: courseError } = useCurrentCourse(courseId ?? '')
  const {
    watchedLessonIds,
    markingLessonIds,
    markLessonAsWatched,
    error: progressError,
  } = useCurrentProgress(courseId ?? '')
  const [currentLesson, setCurrentLesson] = useState<CourseDetailsLesson | null>(null)
  const [currentLessonId, setCurrentLessonId] = useState('')
  const [isDownloadingCertificate, setIsDownloadingCertificate] = useState(false)
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

  const watchedCount = useMemo(() => watchedLessonIds.size, [watchedLessonIds])
  const progressPercent = useMemo(
    () => (lessonsCount > 0 ? Math.round((watchedCount / lessonsCount) * 100) : 0),
    [watchedCount, lessonsCount]
  )

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

  async function handleDownloadCertificate() {
    if (!courseId || !allWatched || isDownloadingCertificate) return

    setIsDownloadingCertificate(true)

    try {
      const response = await authAxiosInstance.get(`/api/certificate/issue/${courseId}`, {
        responseType: 'blob',
      })

      const blobUrl = window.URL.createObjectURL(response.data)
      const downloadLink = document.createElement('a')
      const fileName = `${course?.course.name.slug ?? 'certificado'}.pdf`

      downloadLink.href = blobUrl
      downloadLink.download = fileName
      document.body.appendChild(downloadLink)
      downloadLink.click()
      downloadLink.remove()
      window.URL.revokeObjectURL(blobUrl)
    } finally {
      setIsDownloadingCertificate(false)
    }
  }

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
            onDownloadCertificate={handleDownloadCertificate}
            isDownloadingCertificate={isDownloadingCertificate}
            certificateDisabled={!allWatched}
          />
        </div>
      </section>
    </main>
  )
}
