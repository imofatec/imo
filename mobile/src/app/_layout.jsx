import "../../global.css";
import { Stack } from 'expo-router';
import { View } from 'react-native';
import Header from './outlet/header';
import { usePathname } from 'expo-router';
import Constants from 'expo-constants';

const statusBarHeight = Constants.statusBarHeight;


export default function RootLayout() {
  const pathname = usePathname();

  const hideHeader = ['/login', '/register'].includes(pathname);

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
      </Stack>
      
    </>
  );
}
