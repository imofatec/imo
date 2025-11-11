import "../../global.css";
import { Stack } from "expo-router";
import { View } from "react-native";
import Constants from "expo-constants";
import Header from "./outlet/header";
import { useAuthRedirect } from "../hooks/useAuthRedirect";
import Toast from "react-native-toast-message";

const statusBarHeight = Constants.statusBarHeight;

export default function RootLayout() {
  const { ready, isAuthenticated, segments } = useAuthRedirect();

  const hideHeader = segments.length > 0 && ['login', 'register'].includes(segments[0]);

  if (!ready) return null;

  return (
    <>
      {!hideHeader && (
        <View className="bg-custom-primary" style={{ paddingTop: statusBarHeight }}>
          <Header />
        </View>
      )}
      <Stack screenOptions={{ headerShown: false }}>
        <Stack.Screen name="login" />
        <Stack.Screen name="register" />
        <Stack.Screen name="(tabs)" />
        <Stack.Screen name="watch" />
      </Stack>

      <Toast />
    </>
  );
}
