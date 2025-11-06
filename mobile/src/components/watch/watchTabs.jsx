import React from "react";
import { View, Pressable, Text } from "react-native";

export default function WatchTabs({ tab, setTab, commentsCount }) {
  return (
    <View className="mt-2 px-2 flex-row gap-2">
      <Pressable
        onPress={() => setTab("lessons")}
        className={`px-4 py-2 rounded-full ${tab === "lessons" ? "bg-white" : "bg-white/10"}`}
      >
        <Text className={`${tab === "lessons" ? "text-black" : "text-white"}`}>Aulas</Text>
      </Pressable>

      <Pressable
        onPress={() => setTab("comments")}
        className={`px-4 py-2 rounded-full flex-row items-center gap-2 ${tab === "comments" ? "bg-white" : "bg-white/10"}`}
      >
        <Text className={`${tab === "comments" ? "text-black" : "text-white"}`}>Comentários</Text>
        <View className={`px-2 py-0.5 rounded-full ${tab === "comments" ? "bg-black/10" : "bg-white/20"}`}>
          <Text className={`${tab === "comments" ? "text-black/80" : "text-white/90"} text-xs`}>
            {commentsCount}
          </Text>
        </View>
      </Pressable>
    </View>
  );
}
