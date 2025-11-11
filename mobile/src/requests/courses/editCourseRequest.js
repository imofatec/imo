import { apiFetch } from "../../api/apiFetch";
import { safeAwait } from "../../lib/safeAwait";

export async function editCourseRequest(courseId, data) {
    const submission = {
        name: data.nameCourse,
        category: data.category,
        level: data.level,
        description: data.description
    };
    const [error, result] = await safeAwait(
        apiFetch(`/api/course/${courseId}`, {
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