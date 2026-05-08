import { ChevronDown, ChevronUp } from 'lucide-react'
import { useState } from 'react'
import CommentInput from '@/components/watch/CommentInput'
import CommentList from '@/components/watch/CommentList'
import type { CommentData } from '@/schemas/comments/commentSchema'
import type { UserSummary } from '@/types/user'
import type { LessonComment } from '@/types/watch'

type Props = {
  comments: LessonComment[]
  usersById?: Record<string, UserSummary>
  onSubmitComment?: (comment: CommentData) => Promise<void> | void
}

export default function CommentsTabContent({ comments, usersById = {}, onSubmitComment }: Props) {
  const [isCollapsed, setIsCollapsed] = useState(false)

  return (
    <section className="p-5">
      <div className="flex items-center justify-between gap-3">
        <h3 className="text-base font-semibold text-white">Comentarios ({comments.length})</h3>

        <button
          type="button"
          onClick={() => setIsCollapsed((prev) => !prev)}
          className="inline-flex items-center gap-2 rounded-lg border border-white/15 bg-white/5 px-3 py-2 text-sm text-white/80 transition hover:bg-white/10 hover:text-white"
        >
          {isCollapsed ? 'Expandir' : 'Retrair'}
          {isCollapsed ? <ChevronDown size={16} /> : <ChevronUp size={16} />}
        </button>
      </div>

      <div className="mt-5 space-y-4">
        {!isCollapsed ? <CommentList comments={comments} usersById={usersById} /> : null}
        <CommentInput onSubmitComment={onSubmitComment} />
      </div>
    </section>
  )
}
