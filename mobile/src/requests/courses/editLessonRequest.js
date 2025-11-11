import { apiFetch } from "../../api/apiFetch";
import { safeAwait } from "../../lib/safeAwait";

export async function editLessonRequest(lessonId, data) {
    const submission = {
        title: data.nameLesson,
        youtubeLink: data.link,
        description: data.descriptionL,
    };
    const [error, result] = await safeAwait(
        apiFetch(`/api/lesson/${lessonId}`, {
            method: "PUT",
            body: JSON.stringify(submission),
        })
    );
    if (error) {
        const message = error.message;
        return { success: false, error: message };
    }
    return { success: true, data: result };
}