import authAxiosInstance from '@/api/authAxiosInstance'
import { safeAwait } from '@/lib/safeAwait'


export async function deleteLessonRequest(lessonId: string) {
  const [error, response] = await safeAwait(authAxiosInstance.delete(`/api/lesson/${lessonId}`))

  if (error || !response) throw error

  return response.data
}
