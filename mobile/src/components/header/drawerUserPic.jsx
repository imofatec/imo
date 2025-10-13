import React from 'react';
import { View, Text, Image } from 'react-native';

export default function DrawerUserPic() {
    return (
        <View className="flex items-center gap-1 pt-2">
            <Image source={require('../../assets/imgs/userPic.webp')} className="w-12 h-12 rounded-full" />
            <Text className="text-white text-sm font-semibold">Nome do Usuário</Text>
        </View>
    );
}