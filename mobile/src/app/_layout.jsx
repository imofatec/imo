import "../../global.css";
import { useEffect, useState } from 'react';
import { Stack,useSegments } from 'expo-router';
import { View } from 'react-native';
import Header from './outlet/header';
import Constants from 'expo-constants';

const statusBarHeight = Constants.statusBarHeight;


export default function RootLayout() {
  const segments = useSegments();
  const [ready, setReady] = useState(false);

  useEffect(() => {
    setReady(true);
  }, []);

  const hideHeader = segments.length > 0 && ['login', 'register'].includes(segments[0]);

  if (!ready) {
    return null;
  }

  return (
    <>
      {!hideHeader &&
        <View className="bg-custom-primary" style={{ paddingTop: statusBarHeight }}>
          <Header />
        </View>
      }
      <Stack screenOptions={{ headerShown: false }}>
        <Stack.Screen name="login" />
        <Stack.Screen name="register" />
        <Stack.Screen name="(tabs)" />
        <Stack.Screen name="watch" />
      </Stack>

    </>
  );
}
