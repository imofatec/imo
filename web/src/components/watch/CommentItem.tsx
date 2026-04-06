import type { LessonComment } from '@/types/watch'

type Props = {
  comment: LessonComment
}

function getInitials(name: string) {
  const parts = name.trim().split(' ').filter(Boolean)
  return parts
    .slice(0, 2)
    .map((part) => part[0]?.toUpperCase() ?? '')
    .join('')
}

export default function CommentItem({ comment }: Props) {
  return (
    <article className="p-4">
      <div className="flex items-start gap-3">
        <div className="flex h-9 w-9 shrink-0 items-center justify-center rounded-full border border-white/40 bg-white/5 text-xs font-semibold text-white/85">
          {getInitials(comment.author)}
        </div>

        <div className="min-w-0 flex-1">
          <div className="flex items-center justify-between gap-3">
            <p className="truncate text-sm font-medium text-white">{comment.author}</p>
            <span className="text-xs text-white/50">{comment.createdAt}</span>
          </div>
          <p className="mt-2 text-sm leading-6 text-white/80">{comment.message}</p>
        </div>
      </div>
    </article>
  )
}
