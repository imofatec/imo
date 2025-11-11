import { apiFetch } from "../../api/apiFetch";
import { safeAwait } from "../../lib/safeAwait";

export async function deleteCourseRequest(courseId) {

    const [error, result] = await safeAwait(
        apiFetch(`/api/course/${courseId}`, {
            method: "PATCH",
        })
    );
    if (error) {
        const message = error.message;
        return { success: false, error: message };
    }
    return { success: true, data: result };
}