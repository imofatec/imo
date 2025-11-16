import authAxiosInstance from '@/api/authAxiosInstance'
import { safeAwait } from '@/lib/safeAwait'
import { redirect } from 'react-router-dom'


export async function updateLesson({ request }) {

    const data = await request.formData()
    
    const id = data.get('idCourse')
    const lesson = {
        title: data.get('title'),
        description: data.get('descriptionC'),
        youtubeLink: data.get('youtubeLink')
    }

    const [error, result] = await safeAwait(
        authAxiosInstance.put(`/api/courses/lessons/${id}`, lesson),
    )
    if (error) {
        return { error: error.response.data.message }
    }

    //return redirect('/user/cursos/submissoes')
}