import React from "react";
import { View, Text, Image, Pressable } from "react-native";

export default function ContinueCard({ data, onPress }) {
  return (
    <Pressable onPress={onPress} className="px-6 mt-4">
      <View className="flex-row rounded-2xl bg-white/5 border border-white/10 overflow-hidden">
        <Image source={{ uri: data.image }} className="w-28 h-28" />
        <View className="flex-1 p-3 justify-center">
          <Text className="text-white font-extrabold" numberOfLines={2}>
            {data.title}
          </Text>
          <Text className="text-white/70 text-xs mt-1">{data.instructor}</Text>
          <Text className="text-white/80 text-sm mt-2">
            Progresso: {data.progress}%
          </Text>
          <View className="w-full h-2 bg-white/10 rounded-full mt-2 overflow-hidden">
            <View className="h-2 bg-white" style={{ width: `${data.progress}%` }} />
          </View>
        </View>
      </View>
    </Pressable>
  );
}
