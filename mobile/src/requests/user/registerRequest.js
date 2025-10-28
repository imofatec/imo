import { apiFetch } from "../../api/apiFetch";
import { safeAwait } from "../../lib/safeAwait";

export async function registerRequest(data) {
  const submission = {
    name: data.user,
    email: data.email,
    password: data.password,
    confPassword: data.confirmPassword,
  };

  const [error, result] = await safeAwait(
    apiFetch("/api/user", {
      method: "POST",
      body: JSON.stringify(submission),
      skipAuth: true,
    })
  );

  if (error) {
    const message = error.message || "Erro ao registrar usuário";
    if (message.includes("email")) {
      return { success: false, error: "Este e-mail já está em uso." };
    }

    return { success: false, error: message };
  }

  return { success: true, data: result };
}
