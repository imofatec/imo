import { apiFetch } from "../../api/apiFetch";
import { safeAwait } from "../../lib/safeAwait";

export async function markLessonAsWatchedRequest(lessonId) {
    const [error, result] = await safeAwait(
        apiFetch(`/api/progress/${lessonId}`, {
            method: "PUT",
        })
    );
    if (error) {
        const message = error.message;
        return { success: false, error: message };
    }
    return { success: true, data: result };
}