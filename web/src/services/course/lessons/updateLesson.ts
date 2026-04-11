import authAxiosInstance from '@/api/authAxiosInstance'
import { safeAwait } from '@/lib/safeAwait'

type UpdateLessonPayload = {
  title?: string
  description?: string
  youtubeLink?: string
}

export async function updateLessonRequest(lessonId: string, data: UpdateLessonPayload) {
  const [error, response] = await safeAwait(authAxiosInstance.put(`/api/lesson/${lessonId}`, data))

  if (error || !response) throw error

  return response.data
}
