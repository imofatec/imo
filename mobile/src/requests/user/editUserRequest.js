import { apiFetch } from "../../api/apiFetch";
import { safeAwait } from "../../lib/safeAwait";

export async function editUserRequest(data) {
    const submission = {
        email: data.email,
        name: data.user,
        password: data.password,
    };
    const [error, result] = await safeAwait(
        apiFetch(`/api/user`, {
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