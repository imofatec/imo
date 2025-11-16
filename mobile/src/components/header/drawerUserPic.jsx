import React from 'react';
import { View, Text, Image } from 'react-native';
import { useCurrentUser } from "../../hooks/useCurrentUser";

export default function DrawerUserPic() {
    const { user, urlImage, loading } = useCurrentUser();

    return (
        <View className="flex gap-1 pt-2 w-full items-start">
            <Image
                source={urlImage ? { uri: urlImage } : require('../../assets/imgs/userPic.webp')}
                className="w-12 h-12 rounded-full"
            />
            <Text className="text-white text-sm font-semibold pl-2">
                {loading ? "Carregando..." : user?.name || "Usuário"}
            </Text>
        </View>
    );
}
