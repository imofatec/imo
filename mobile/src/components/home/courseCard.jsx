import React from "react";
import { View, Text, Image, Pressable } from "react-native";

export default function CourseCardSmall({ item, onPress }) {
  return (
    <Pressable onPress={onPress} className="w-[46%] mb-5">
      <View className="rounded-2xl overflow-hidden bg-white/5 border border-white/10">
        <Image source={{ uri: item.image }} className="w-full h-28" />
        <View className="p-3">
          <Text numberOfLines={2} className="text-white font-semibold">
            {item.title}
          </Text>
          <Text className="text-white/50 text-xs mt-1">Nome do instrutor</Text>
        </View>
      </View>
    </Pressable>
  );
}
