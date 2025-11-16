import authAxiosInstance from '@/api/authAxiosInstance'
import { safeAwait } from '@/lib/safeAwait'
import { redirect } from 'react-router-dom'


export async function updateCourse({ request }) {

    const data = await request.formData()
    
    const id = data.get('idCourse')
    const course = {
        name: data.get('name'),
        category: data.get('category'),
        level: data.get('level'),
        description: data.get('description')
    }

    

    const [error, result] = await safeAwait(
        authAxiosInstance.put(`/api/courses/${id}`, course),
    )
    if (error) {
        return { error: error.response.data.message }
    }

    return redirect('/user/cursos/submissoes')
}