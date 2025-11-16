import React from "react";
import { Pressable, Text, View } from "react-native";
import { Ionicons } from "@expo/vector-icons";

export default function LessonCard({ lesson, isActive, onPress, watched, onToggleWatched, disabled }) {
  return (
    <Pressable
      onPress={onPress}
      className={`mb-3 p-4 rounded-2xl flex-row justify-between items-start ${isActive ? "bg-white/10 border border-white/30" : "bg-white/5"
        }`}
    >
      <View className="flex-1 pr-3">
        <Text className="text-lg font-semibold text-white">{lesson.title}</Text>
        {lesson.description ? (
          <Text className="text-gray-300 text-sm mt-1">{lesson.description}</Text>
        ) : null}
      </View>
      <View className="items-end">
        <Pressable
          onPress={() => !disabled && onToggleWatched(lesson.id)}
          disabled={disabled}
          className={`px-3 py-1 rounded-full mb-2 ${disabled
            ? "bg-green-400 opacity-70"
            : watched
              ? "bg-green-400"
              : "bg-white/10"
            }`}
        >
          <Text className={disabled ? "text-black font-bold opacity-80" : watched ? "text-black font-bold": "text-white text-sm"}>
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