import React from "react";
import { Pressable, Text, View } from "react-native";
import { Ionicons } from "@expo/vector-icons";

export default function LessonCard({ lesson, isActive, onPress, watched, onToggleWatched }) {
  return (
    <Pressable
      onPress={onPress}
      className={`mb-3 p-4 rounded-2xl flex-row justify-between items-start ${
        isActive ? "bg-white/10 border border-white/30" : "bg-white/5"
      }`}
    >
      <View className="flex-1 pr-3">
        <Text className={`text-lg font-semibold ${isActive ? "text-white" : "text-white"}`}>
          {lesson.title}
        </Text>
        {lesson.description ? (
          <Text className="text-gray-300 text-sm mt-1">{lesson.description}</Text>
        ) : null}
      </View>

      <View className="items-end">
        <Pressable
          onPress={() => onToggleWatched(lesson.id)}
          className={`px-3 py-1 rounded-full mb-2 ${
            watched ? "bg-green-400" : "bg-white/10"
          }`}
        >
          <Text className={watched ? "text-black font-bold" : "text-white text-sm"}>
            {watched ? "Assistida" : "Marcar"}
          </Text>
        </Pressable>

        <View className="w-8 h-8 items-center justify-center rounded-full border border-white/20">
          <Ionicons name={isActive ? "play" : "play-outline"} size={18} color="white" />
        </View>
      </View>
    </Pressable>
  );
}
