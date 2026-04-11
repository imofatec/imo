import CommentsTabContent from '@/components/watch/CommentsTabContent'
import { useAllComments } from '@/hooks/useAllComments'
import { useUsersByIds } from '@/hooks/useUsersByIds'
import type { CommentData } from '@/schemas/comments/commentSchema'
import { createCommentRequest } from '@/services/comments/createComment'

type Props = {
  lessonId?: string
}

export default function LessonCommentsSection({ lessonId }: Props) {
  const { comments, refetch: refetchComments } = useAllComments(lessonId ?? '', {
    enabled: Boolean(lessonId),
  })

  const commentUserIds = comments.map((comment) => comment.userId)
  const { usersById } = useUsersByIds(commentUserIds, {
    enabled: commentUserIds.length > 0,
  })

  async function handleSubmitComment(data: CommentData) {
    if (!lessonId) return

    await createCommentRequest(lessonId, data)
    await refetchComments()
  }

  return (
    <CommentsTabContent
      comments={comments}
      usersById={usersById}
      onSubmitComment={handleSubmitComment}
    />
  )
}
