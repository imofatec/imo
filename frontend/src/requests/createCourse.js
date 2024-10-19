import api from '@/api/api'
import { safeAwait } from '@/lib/safeAwait'
import { redirect } from 'react-router-dom'

export async function createCourse({ request }) {
  const data = await request.formData()
  const lessons = []

  let lessonIndex = 0
  while (data.get(`title-${lessonIndex}`)) {
    lessons.push({
      title: data.get(`title-${lessonIndex}`),
      description: data.get(`descriptionC-${lessonIndex}`),
      youtubeLink: data.get(`youtubeLink-${lessonIndex}`),
    })
    lessonIndex++
  }

  const course = {
    name: data.get('name'),
    description: data.get('description'),
    category: data.get('category'),
    lessons: lessons,
  }

  const [error, result] = await safeAwait(
    api.post('/api/courses/create', course, {
      headers: {
        'Content-Type': 'application/json',
      },
    }),
  )
  if (error) {
    return { error: error.response.data.message || 'Erro ao criar curso'}
  }
  return redirect('/categorias')
}
