import CommentItem from '@/components/watch/CommentItem'
import type { LessonComment } from '@/types/watch'

type Props = {
  comments: LessonComment[]
}

export default function CommentList({ comments }: Props) {
  return (
    <div>
      {comments.length > 0 ? (
        comments.map((comment) => <CommentItem key={comment.id} comment={comment} />)
      ) : (
        <div className="p-4">
          <p className="text-sm text-white/70">Nenhum comentário nesta aula.</p>
        </div>
      )}
    </div>
  )
}
