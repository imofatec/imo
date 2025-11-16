import { apiFetch } from "../../api/apiFetch";
import { safeAwait } from "../../lib/safeAwait";

export async function createNewLessonRequest(courseId,data) {
    const submission = {
        title: data.title,
        youtubeLink: data.youtubeLink,
        description: data.descriptionL,
    };
    const [error, result] = await safeAwait(
        apiFetch(`/api/lesson/${courseId}`, {
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