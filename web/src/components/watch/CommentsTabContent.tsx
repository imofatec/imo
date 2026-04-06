import CommentInput from '@/components/watch/CommentInput'
import CommentList from '@/components/watch/CommentList'
import type { LessonComment } from '@/types/watch'

type Props = {
  comments: LessonComment[]
  onSubmitComment?: (comment: string) => void
}

export default function CommentsTabContent({ comments, onSubmitComment }: Props) {
  return (
    <section className="p-5">
      <h3 className="text-base font-semibold text-white">Comentários ({comments.length})</h3>

      <div className="mt-5 space-y-4">
        <CommentList comments={comments} />
        <CommentInput onSubmitComment={onSubmitComment} />
      </div>
    </section>
  )
}
