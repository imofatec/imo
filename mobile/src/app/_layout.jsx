import "../../global.css";
import { Stack } from 'expo-router';
import { View } from 'react-native';
import Header from './outlet/header';
import { usePathname } from 'expo-router';
import Constants from 'expo-constants';

const statusBarHeight = Constants.statusBarHeight;


export default function RootLayout() {
  const pathname = usePathname();

  const hideHeader = ['/login'].includes(pathname);

  return (
    <>
      {!hideHeader &&
        <View className="bg-custom-primary" style={{ paddingTop: statusBarHeight }}>
          <Header />
        </View>
      }
      <Stack>
        <Stack.Screen name="(tabs)" options={{ headerShown: false }} />
      </Stack>
    </>
  );
}
