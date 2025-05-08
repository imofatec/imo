import authAxiosInstance from '@/api/authAxiosInstance'
import { safeAwait } from '@/lib/safeAwait'


export async function createComment({ request}) {

    const data = await request.formData()
    const id = data.get('lessonId')
    const comment = {
        comment: data.get('comment'),
    }

    const [error, result] = await safeAwait(
        authAxiosInstance.post(`/api/courses/comments/lessons/${id}`, comment),
    )
    if (error) {
        return { error: error.response.data.message }
    }

    return { success: 'Comentario criado com sucesso' }
}
