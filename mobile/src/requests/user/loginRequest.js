import { apiFetch } from "../../api/apiFetch";
import { safeAwait } from "../../lib/safeAwait";
import AsyncStorage from "@react-native-async-storage/async-storage";

export async function loginRequest(data) {
  const submission = {
    email: data.email,
    password: data.password,
  };

  const [error, result] = await safeAwait(
    apiFetch("/api/user/login", {
      method: "POST",
      body: JSON.stringify(submission),
    })
  );

  if (error) {
    const message = error.message;
    return { success: false, error: message };
  }

  if (result?.accessToken) {
    await AsyncStorage.setItem("token", result.accessToken);
  }

  return { success: true, data: result };
}
