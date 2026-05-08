import UserAvatar from '@/components/ui/UserAvatar'
import type { UserSummary } from '@/types/user'
import type { LessonComment } from '@/types/watch'
import { Link } from 'react-router-dom'

type Props = {
  comment: LessonComment
  author?: UserSummary
}

export default function CommentItem({ comment, author }: Props) {
  const authorName = author?.name ?? comment.userId
  const profileImageSrc = author?.profilePicturePath ?? null
  const profilePath = `/social/${comment.userId}`

  return (
    <article className="p-4">
      <div className="flex items-start gap-3">
        <UserAvatar
          imageSrc={profileImageSrc}
          name={authorName}
          fallback="initials"
          className="shrink-0"
        />

        <div className="min-w-0 flex-1">
          <div className="flex items-center justify-between gap-3">
            <Link
              to={profilePath}
              className="truncate text-sm font-medium text-white transition hover:text-cyan hover:underline"
            >
              {authorName}
            </Link>
          </div>
          <p className="mt-2 text-sm leading-6 text-white/80">{comment.content}</p>
        </div>
      </div>
    </article>
  )
}
