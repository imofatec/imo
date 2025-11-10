import { useEffect, useState } from "react";
import { useRouter, useSegments } from "expo-router";
import AsyncStorage from "@react-native-async-storage/async-storage";

export function useAuthRedirect() {
  const segments = useSegments();
  const router = useRouter();
  const [ready, setReady] = useState(false);
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  async function checkAuth() {
    try {
      const token = await AsyncStorage.getItem("token");
      setIsAuthenticated(!!token);
    } catch {
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
