import AsyncStorage from "@react-native-async-storage/async-storage";
import { baseURL } from "./enviroment";

export async function apiFetch(endpoint, options = {}) {
  const skipAuth = options.skipAuth || false;
  let token = null;

  if (!skipAuth) {
    token = await AsyncStorage.getItem("token");
  }

  const headers = {
    "Content-Type": "application/json",
    ...(token ? { Authorization: `Bearer ${token}` } : {}),
    ...options.headers,
  };

  const response = await fetch(`${baseURL}${endpoint}`, {
    ...options,
    headers,
  });

  if (!response.ok) {
    let errorMessage = `Erro ${response.status}`;

    try {
      const errorData = await response.json();
      errorMessage = errorData.message || errorMessage;
    } catch {
      const text = await response.text();
      errorMessage = text || errorMessage;
    }

    throw new Error(errorMessage);
  }

  return response.json();
}
