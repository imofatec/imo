import authAxiosInstance from '@/api/authAxiosInstance'
import { safeAwait } from '@/lib/safeAwait'
import type { CommentData } from '@/schemas/comments/commentSchema'

type CreateCommentPayload = {
  content: string
  parentId?: string
}

function toCreateCommentPayload(data: CommentData): CreateCommentPayload {
  return {
    content: data.content,
    ...(data.parentId ? { parentId: data.parentId } : {}),
  }
}

export async function createCommentRequest(lessonId: string, data: CommentData) {
  const payload = toCreateCommentPayload(data)
  const [error, response] = await safeAwait(
    authAxiosInstance.post(`/api/comment/${lessonId}`, payload)
  )

  if (error || !response) throw error

  return response.data
}
