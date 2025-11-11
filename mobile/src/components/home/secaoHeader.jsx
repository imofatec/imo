import React from "react";
import { View, Text, Pressable } from "react-native";
import { Ionicons } from "@expo/vector-icons";

export default function SectionHeader({ title, onSeeAll }) {
  return (
    <View className="px-6 mt-6 mb-3 flex-row items-center justify-between">
      <Text className="text-white text-xl font-extrabold">{title}</Text>
      {onSeeAll && (
        <Pressable onPress={onSeeAll} className="flex-row items-center">
          <Text className="text-white/80 mr-1">Ver todos</Text>
          <Ionicons name="chevron-forward" size={18} color="#fff" />
        </Pressable>
      )}
    </View>
  );
}
