import { apiFetch } from "../../api/apiFetch";
import { safeAwait } from "../../lib/safeAwait";

export async function createCourseRequest(data) {
    const submission = {
        name: data.nameCourse,
        category: data.category,
        level: data.level,
        description: data.description,
        lessons: data.lessons.map(lesson => ({
            title: lesson.nameLesson,
            description: lesson.descriptionL,
            youtubeLink: lesson.link
        }))
    };
    const [error, result] = await safeAwait(
        apiFetch("/api/course", {
            method: "POST",
            body: JSON.stringify(submission),
        })
    );

    if (error) {
        const message = error.message;
        return { success: false, error: message };
    }

    return { success: true, data: result };
}