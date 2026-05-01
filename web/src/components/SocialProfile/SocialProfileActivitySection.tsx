import ProfileCourseCard from '@/components/SocialProfile/ProfileCourseCard'
import LinkButton from '@/components/ui/LinkButton'
import {
  formatSocialProfileDateTime,
  getSocialProfileWatchPath,
} from '@/lib/socialProfile'
import type { PublicProfileLastComment, PublicProfileRecentCourse } from '@/types/publicUserProfile'
import { Clock3, MessageSquareMore } from 'lucide-react'

type SocialProfileActivitySectionProps = {
  recentCourses: PublicProfileRecentCourse[]
  lastComment: PublicProfileLastComment | null
}

type RecentCourseCardProps = {
  course: PublicProfileRecentCourse
}

function getRecentCourseMetadata(course: PublicProfileRecentCourse) {
  return `${course.watchedLessonsCount} de ${course.totalLessonsCount} aulas concluidas`
}

function getRecentCourseImageUrl(firstLessonYoutubeLink: string) {
  return `https://img.youtube.com/vi/${firstLessonYoutubeLink}/maxresdefault.jpg`
}

function RecentCourseCard({ course }: RecentCourseCardProps) {
  const coursePath = getSocialProfileWatchPath(course.courseId, course.firstLessonYoutubeLink)

  return (
    <ProfileCourseCard
      title={course.courseName}
      subtitle={getRecentCourseMetadata(course)}
      imageUrl={getRecentCourseImageUrl(course.firstLessonYoutubeLink)}
      to={coursePath ?? '/categorias'}
    />
  )
}

type LastCommentCardProps = {
  comment: PublicProfileLastComment
}

function LastCommentCard({ comment }: LastCommentCardProps) {
  const commentPath = getSocialProfileWatchPath(comment.courseId, comment.lessonYoutubeLink)

  return (
    <article className="rounded-2xl border border-white/10 bg-white/5 p-4">
      <div className="flex items-start justify-between gap-3">
        <div className="min-w-0">
          <p className="text-xs uppercase tracking-[0.22em] text-cyan/75">Comentário recente</p>
          <h3 className="mt-2 text-base font-semibold wrap-break-word text-white">
            {comment.courseName}
          </h3>
          <p className="mt-1 text-sm wrap-break-word text-white/60">Aula: {comment.lessonTitle}</p>
        </div>

        <div className="border-cyan/20 bg-cyan/10 text-cyan flex h-11 w-11 shrink-0 items-center justify-center rounded-2xl border">
          <MessageSquareMore size={20} />
        </div>
      </div>

      <blockquote className="mt-4 rounded-2xl border border-white/8 bg-[#0f0628] px-4 py-3 text-sm leading-6 wrap-break-word text-white/80">
        "{comment.content}"
      </blockquote>

      <div className="mt-4 flex flex-wrap items-center justify-between gap-3 text-xs text-white/55">
        <div className="flex items-center gap-2">
          <Clock3 size={14} className="text-cyan/75" />
          <span>{formatSocialProfileDateTime(comment.createdAt, 'Data não encontrada')}</span>
        </div>

        {commentPath ? (
          <LinkButton
            to={commentPath}
            variant="cyanOutline"
            className="text-cyan border-cyan/40 mt-0! w-auto rounded-full border px-4 py-2"
          >
            Abrir aula
          </LinkButton>
        ) : (
          <span>Link da não encontrado</span>
        )}
      </div>
    </article>
  )
}

export default function SocialProfileActivitySection({
  recentCourses,
  lastComment,
}: SocialProfileActivitySectionProps) {
  const hasRecentCourses = recentCourses.length > 0

  return (
    <section className="rounded-[1.75rem] border border-white/10 bg-[#10052b] p-6">
      <div className="mb-5 border-b border-white/10 pb-4">
        <h2 className="text-lg font-semibold text-white">Atividade recente</h2>
      </div>

      <div className="space-y-6">
        <div>
          <div className="mb-4 flex items-center gap-2">
            <h3 className="text-sm font-semibold tracking-[0.18em] text-white/75 uppercase">Cursos recentes</h3>
          </div>

          {hasRecentCourses ? (
            <div className="grid gap-4 md:grid-cols-2 xl:grid-cols-3">
              {recentCourses.map((course) => (
                <RecentCourseCard key={course.courseId} course={course} />
              ))}
            </div>
          ) : (
            <p className="rounded-2xl border border-white/10 bg-white/5 px-4 py-3 text-sm text-white/60">
              Nenhum curso recente.
            </p>
          )}
        </div>

        <div>
          <div className="mb-4 flex items-center gap-2">
            <MessageSquareMore size={18} className="text-cyan" />
            <h3 className="text-sm font-semibold tracking-[0.18em] text-white/75 uppercase">
              Ultimo comentário
            </h3>
          </div>

          {lastComment ? (
            <LastCommentCard comment={lastComment} />
          ) : (
            <p className="rounded-2xl border border-white/10 bg-white/5 px-4 py-3 text-sm text-white/60">
              Nenhum comentário recente.
            </p>
          )}
        </div>
      </div>
    </section>
  )
}
