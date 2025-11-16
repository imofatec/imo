import React from "react";
import { View, Text, Pressable } from "react-native";
import { Ionicons } from "@expo/vector-icons";
import { router } from "expo-router";

export default function WatchHeader() {
  return (
    <View className="px-6 pt-4 pb-4 flex-row items-center justify-between">
      <View className="flex-row items-center">
        <Pressable onPress={() => router.push("(tabs)/myCourses")} className="mr-3">
          <Ionicons name="arrow-back-outline" size={26} color="white" />
        </Pressable>
        <Text className="text-white text-3xl font-bold">Assistir Aula</Text>
      </View>
      <Ionicons name="play-circle-outline" size={32} color="white" />
    </View>
  );
}
