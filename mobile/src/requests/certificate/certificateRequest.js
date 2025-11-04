import { apiFetch } from "../../api/apiFetch";
import { safeAwait } from "../../lib/safeAwait";

export async function certificateRequest(code) {
    const [error, result] = await safeAwait(
        apiFetch(`/api/certificate/details/${code}`, {
            method: "GET",
        })
    );
    if (error) {
        const message = error.message;
        return { success: false, error: message };
    }

    return { success: true, data: result };
}