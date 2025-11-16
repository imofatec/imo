import { Text, View, Pressable, Animated } from 'react-native';
import { Ionicons } from '@expo/vector-icons';
import DrawerItem from "./drawerItem";
import DrawerUserPic from "./drawerUserPic";
import AsyncStorage from "@react-native-async-storage/async-storage";
import { useRouter } from "expo-router";

export default function DrawerContent({ slideAnim, onClose, drawerItems }) {
    const router = useRouter();

    async function handleLogout() {
        try {
            await AsyncStorage.removeItem("token");
            if (onClose) onClose();
            router.replace("/login");
        } catch (error) {
            console.error("Erro ao fazer logout:", error);
        }
    }
    return (
        <Animated.View
            style={{ transform: [{ translateX: slideAnim }] }}
            className="absolute top-0 left-0 w-10/12 h-screen flex-1 gap-5 bg-custom-primary p-4 border-r z-20"
        >
            <Pressable
                className='right-0 top-2 absolute z-30 h-10 w-10 rounded-full flex items-center justify-center'
                onPress={onClose}
            >
                <Ionicons name="close" size={20} color="white" />
            </Pressable>

            <View className="h-24 flex items-start pl-4">
                <DrawerUserPic />
            </View>

            <View className="flex-grow gap-10 justify-start pl-4 min-h-20">
                {drawerItems.map((item, index) => (
                    <DrawerItem
                        key={index}
                        icon={item.icon}
                        label={item.label}
                        route={item.route}
                        onNavigate={onClose}
                    />
                ))}
            </View>

            <View className="pl-4 justify-end pb-10">
                <Pressable className="mt-10">
                    <Text className="text-white text-lg" onPress={handleLogout}>Sair</Text>
                </Pressable>
            </View>
        </Animated.View>
    );
}