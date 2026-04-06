import authAxiosInstance from '@/api/authAxiosInstance'
import { safeAwait } from '@/lib/safeAwait'
import type { CreateCourseData as CreateCourseFormData } from '@/schemas/courses/CreateCourseSchema'

type CreateCoursePayload = {
  name: string
  category: string
  level: string
  description: string
  lessons: {
    title: string
    description: string
    youtubeLink: string
  }[]
  contributorId: string
}

function toCreateCoursePayload(
  data: CreateCourseFormData,
  contributorId: string
): CreateCoursePayload {
  return {
    name: data.nameCourse,
    category: data.category,
    level: data.level,
    description: data.description,
    contributorId,
    lessons: data.lessons.map((lesson) => ({
      title: lesson.nameLesson,
      description: lesson.descriptionL,
      youtubeLink: lesson.link,
    })),
  }
}

export async function createCourseRequest(data: CreateCourseFormData, contributorId: string) {
  const payload = toCreateCoursePayload(data, contributorId)
  const [error, response] = await safeAwait(authAxiosInstance.post('/api/course', payload))

  if (error || !response) throw error

  return response.data
}
