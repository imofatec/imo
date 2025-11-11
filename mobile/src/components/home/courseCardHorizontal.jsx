import React from "react";
import { View, Text, Image, Pressable } from "react-native";

export default function CourseCardHorizontal({ item, onPress }) {
  return (
    <Pressable onPress={onPress} className="mr-4 w-60">
      <View className="rounded-2xl overflow-hidden bg-white/5 border border-white/10">
        <Image source={{ uri: item.image }} className="w-full h-32" />
        <View className="p-3">
          <Text numberOfLines={2} className="text-white font-semibold">
            {item.title}
          </Text>
          <Text className="text-white/50 text-xs mt-1">{item.author}</Text>
        </View>
      </View>
    </Pressable>
  );
}
