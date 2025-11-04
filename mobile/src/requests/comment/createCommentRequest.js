import { apiFetch } from "../../api/apiFetch";
import { safeAwait } from "../../lib/safeAwait";

export async function createCommentRequest(data) {
    const submission = {
        content: data.content,
        parentId: data.parentId || null,
        
    };
    const [error, result] = await safeAwait(
        apiFetch(`/api/comment/${data.lessonId}`, {
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