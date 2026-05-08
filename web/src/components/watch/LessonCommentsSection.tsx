import CommentsTabContent from '@/components/watch/CommentsTabContent'
import { useAllComments } from '@/hooks/useAllComments'
import { useUsersByIds } from '@/hooks/useUsersByIds'
import { useRequestErrorToast } from '@/lib/requestToast'
import type { CommentData } from '@/schemas/comments/commentSchema'
import { createCommentRequest } from '@/services/comments/createComment'

type Props = {
  lessonId?: string
}

export default function LessonCommentsSection({ lessonId }: Props) {
  const { comments, error: commentsError, refetch: refetchComments } = useAllComments(
    lessonId ?? '',
    {
      enabled: Boolean(lessonId),
    }
  )

  const commentUserIds = comments.map((comment) => comment.userId)
  const { usersById, error: usersError } = useUsersByIds(commentUserIds, {
    enabled: commentUserIds.length > 0,
  })

  useRequestErrorToast(commentsError, { id: 'comments-error' })
  useRequestErrorToast(usersError, { id: 'comment-users-error' })

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
