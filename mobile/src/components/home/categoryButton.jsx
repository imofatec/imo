import React from "react";
import { Pressable, Text } from "react-native";

export default function CategoryChip({ label, onPress }) {
  return (
    <Pressable onPress={onPress} className="px-4 py-2 mr-2 rounded-full bg-white/10 border border-white/10">
      <Text className="text-white">{label}</Text>
    </Pressable>
  );
}
