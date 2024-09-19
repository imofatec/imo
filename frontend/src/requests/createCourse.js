import api from "@/api/api";
import { redirect } from "react-router-dom";

export async function createCourse({ request }) {
    const data = await request.formData();
    const course = {
        name: data.get('name'),
        description: data.get('description'),
        category: data.get('category'),
        lessons: [
            {
                title: data.get('title'),
                description: data.get('descriptionC'),
                youtubeLink: data.get('youtubeLink')
            }
        ] 
    };

    try {
        const response = await api.post('/api/courses/create', course, {
            headers: {
                'Content-Type': 'application/json',
            },
        });
        return redirect('/cursos');
    } catch (error) {
        console.error(error.response.data);
        return { error: error.response.data.message || 'Erro ao criar curso' };
    }
}