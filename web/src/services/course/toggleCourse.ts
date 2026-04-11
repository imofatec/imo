import authAxiosInstance from '@/api/authAxiosInstance'
import { safeAwait } from '@/lib/safeAwait'


export async function toggleCourseRequest(courseId: string) {
  const [error, response] = await safeAwait(authAxiosInstance.patch(`/api/course/${courseId}`))

  if (error || !response) throw error

  return response.data
}
