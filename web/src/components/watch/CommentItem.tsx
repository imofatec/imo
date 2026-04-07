import type { User } from '@/types/user'
import type { LessonComment } from '@/types/watch'

type Props = {
  comment: LessonComment
  author?: User
}

function getInitials(value: string) {
  const parts = value.trim().split(' ').filter(Boolean)
  return parts
    .slice(0, 2)
    .map((part) => part[0]?.toUpperCase() ?? '')
    .join('')
}

export default function CommentItem({ comment, author }: Props) {
  const authorName = author?.name ?? comment.userId

  return (
    <article className="p-4">
      <div className="flex items-start gap-3">
        <div className="flex h-9 w-9 shrink-0 items-center justify-center rounded-full border border-white/40 bg-white/5 text-xs font-semibold text-white/85">
          {getInitials(authorName)}
        </div>

        <div className="min-w-0 flex-1">
          <div className="flex items-center justify-between gap-3">
            <p className="truncate text-sm font-medium text-white">{authorName}</p>
          </div>
          <p className="mt-2 text-sm leading-6 text-white/80">{comment.content}</p>
        </div>
      </div>
    </article>
  )
}
