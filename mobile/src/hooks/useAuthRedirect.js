import { useEffect, useState } from "react";
import { useRouter, useSegments } from "expo-router";
import AsyncStorage from "@react-native-async-storage/async-storage";
import { jwtDecode } from "jwt-decode";

export function useAuthRedirect() {
  const segments = useSegments();
  const router = useRouter();
  const [ready, setReady] = useState(false);
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  function isTokenValid(token) {
    try {
      const decoded = jwtDecode(token);

      if (!decoded.exp) return false;
      
      const currentTime = Date.now() / 1000;
      return decoded.exp > currentTime;
    } catch (error) {
      console.error("Erro ao decodificar token:", error);
      return false;
    }
  }

  async function checkAuth() {
    try {
      const token = await AsyncStorage.getItem("token");
      if (!token) {
        setIsAuthenticated(false);
        return;
      }
      if (!isTokenValid(token)) {
        await AsyncStorage.removeItem("token");
        setIsAuthenticated(false);
        return;
      }
      setIsAuthenticated(true);
    } catch (error) {
      console.error("Erro ao verificar autenticação:", error);
      setIsAuthenticated(false);
    } finally {
      setReady(true);
    }
  }
  useEffect(() => {
    checkAuth();
  }, [segments]);

  useEffect(() => {
    if (!ready) return;
    const inAuthGroup = segments[0] === "login" || segments[0] === "register";

    if (isAuthenticated && inAuthGroup) {
      router.replace("/(tabs)/home");
    } else if (!isAuthenticated && !inAuthGroup && segments[0] !== undefined) {
      router.replace("/login");
    }
  }, [ready, isAuthenticated, segments]);

  return { ready, isAuthenticated, segments };
}