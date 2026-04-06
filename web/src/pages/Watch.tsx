import { useMemo, useState } from 'react'
import CommentsTabContent from '@/components/watch/CommentsTabContent'
import LessonTabContent from '@/components/watch/LessonTabContent'
import PlayerHeader from '@/components/watch/PlayerHeader'
import WatchHeader from '@/components/watch/WatchHeader'
import type { LessonComment, WatchLesson } from '@/types/watch'

const watchLessons: WatchLesson[] = [
  {
    id: 'lesson-1',
    order: 1,
    title: 'Boas vindas e visão geral',
    description:
      'Uma introdução rápida sobre o curso, os objetivos das aulas e como aproveitar melhor o conteúdo.',
    youtubeId: '0Ssi-9wS1so',
    duration: '08:20',
    watched: false,
  },
  {
    id: 'lesson-2',
    order: 2,
    title: 'Setup de ambiente',
    description:
      'Configuração inicial do projeto, padrão de pastas e ajustes para acelerar o fluxo de desenvolvimento.',
    youtubeId: 'K_yDB4LKSBc',
    duration: '14:09',
    watched: false,
  },
  {
    id: 'lesson-3',
    order: 3,
    title: 'Composição de componentes',
    description:
      'Separação de responsabilidades entre layout, conteúdo e elementos reutilizáveis da interface.',
    youtubeId: 'urZWmEgm72Q',
    duration: '22:31',
    watched: false,
  },
  {
    id: 'lesson-4',
    order: 4,
    title: 'Estados e interações',
    description:
      'Estratégias para controlar estado local da tela e interações basicas entre tabs, comentários e aulas.',
    youtubeId: 'HcrRASQv0LA',
    duration: '18:42',
    watched: false,
  },
]

const commentsByLesson: Record<string, LessonComment[]> = {
  'lesson-1': [
    {
      id: 'comment-1',
      author: 'Daniel Azevedo',
      message: 'Muito legal daora mano',
      createdAt: 'há 1 dia',
    },
    {
      id: 'comment-2',
      author: 'Matheus Nicolas',
      message: 'Dando ideias',
      createdAt: 'há 3 dias',
    },
  ],
  'lesson-2': [
    {
      id: 'comment-3',
      author: 'Abner Cerqueira',
      message: 'Gosto muito dessa banda',
      createdAt: 'há 6 horas',
    },
  ],
  'lesson-3': [
    {
      id: 'comment-4',
      author: 'Vastobode',
      message: 'Eu sou o Vastobode',
      createdAt: 'há 2 horas',
    },
  ],
}

export default function WatchPage() {
  const [currentLessonId, setCurrentLessonId] = useState(watchLessons[0].id)
  const [watchedLessonIds, setWatchedLessonIds] = useState<Set<string>>(() => new Set())

  const currentLesson = useMemo(
    () => watchLessons.find((lesson) => lesson.id === currentLessonId) ?? watchLessons[0],
    [currentLessonId]
  )

  const watchedCount = useMemo(() => watchedLessonIds.size, [watchedLessonIds])

  const progressPercent = useMemo(
    () => Math.round((watchedCount / watchLessons.length) * 100),
    [watchedCount]
  )
  const allWatched = watchedCount === watchLessons.length

  const currentComments = commentsByLesson[currentLesson.id] ?? []

  function handleToggleLessonWatched(lessonId: string) {
    setWatchedLessonIds((prev) => {
      const next = new Set(prev)

      if (next.has(lessonId)) {
        next.delete(lessonId)
      } else {
        next.add(lessonId)
      }

      return next
    })
  }

  return (
    <main className="min-h-screen w-full bg-[#0C0424]">
      <WatchHeader
        courseTitle="React para web na prática"
        watchedCount={watchedCount}
        lessonsCount={watchLessons.length}
        progressPercent={progressPercent}
      />

      <section className="mx-auto w-full max-w-360 px-4 py-8 sm:px-6 lg:px-8">
        <div className="grid gap-6 xl:grid-cols-[minmax(0,1.6fr)_minmax(320px,1fr)] xl:gap-0">
          <div className="space-y-6 xl:pr-6">
            <PlayerHeader lesson={currentLesson} />
            <CommentsTabContent comments={currentComments} />
          </div>
          <LessonTabContent
            lessons={watchLessons}
            watchedCount={watchedCount}
            progressPercent={progressPercent}
            currentLessonId={currentLesson.id}
            onSelectLesson={setCurrentLessonId}
            watchedLessonIds={watchedLessonIds}
            onToggleLessonWatched={handleToggleLessonWatched}
            certificateDisabled={!allWatched}
          />
        </div>
      </section>
    </main>
  )
}
