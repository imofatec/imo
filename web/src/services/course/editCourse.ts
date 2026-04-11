import authAxiosInstance from '@/api/authAxiosInstance'
import { safeAwait } from '@/lib/safeAwait'

type EditCoursePayload = {
  name?: string
  category?: string
  level?: string
  description?: string
}

export async function editCourseRequest(courseId: string, data: EditCoursePayload) {
  const [error, response] = await safeAwait(authAxiosInstance.put(`/api/course/${courseId}`, data))

  if (error || !response) throw error

  return response.data
}
