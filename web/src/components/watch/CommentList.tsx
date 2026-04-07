import CommentItem from '@/components/watch/CommentItem'
import type { User } from '@/types/user'
import type { LessonComment } from '@/types/watch'

type Props = {
  comments: LessonComment[]
  usersById?: Record<string, User>
}

export default function CommentList({ comments, usersById = {} }: Props) {
  return (
    <div>
      {comments.length > 0 ? (
        comments.map((comment) => (
          <CommentItem key={comment.id} comment={comment} author={usersById[comment.userId]} />
        ))
      ) : (
        <div className="p-4">
          <p className="text-sm text-white/70">Nenhum comentario nesta aula.</p>
        </div>
      )}
    </div>
  )
}
