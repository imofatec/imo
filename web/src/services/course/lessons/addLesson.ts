import authAxiosInstance from '@/api/authAxiosInstance'
import { safeAwait } from '@/lib/safeAwait'

type UpdateLessonPayload = {
  title?: string
  description?: string
  youtubeLink?: string
}

export async function addLessonRequest(courseId: string, data: UpdateLessonPayload) {
  const [error, response] = await safeAwait(authAxiosInstance.post(`/api/lesson/${courseId}`, data))

  if (error || !response) throw error

  return response.data
}
