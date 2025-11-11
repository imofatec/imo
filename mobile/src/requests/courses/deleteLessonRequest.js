import { apiFetch } from "../../api/apiFetch";
import { safeAwait } from "../../lib/safeAwait";

export async function deleteLessonRequest(lessonId) {

    const [error, result] = await safeAwait(
        apiFetch(`/api/lesson/${lessonId}`, {
            method: "DELETE",
        })
    );
    if (error) {
        const message = error.message;
        return { success: false, error: message };
    }
    return { success: true, data: result };
}