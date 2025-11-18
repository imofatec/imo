import React from "react";
import { View } from "react-native";

export default function SkeletonLessonProgress() {
  return (
    <View className="mt-4 px-2 animate-pulse">
      <View className="w-40 h-4 bg-gray-700/50 rounded mb-2" />
      
      <View className="w-full h-3 bg-gray-700/30 rounded-full overflow-hidden">
        <View className="h-full w-1/3 bg-gray-700/50" />
      </View>
    </View>
  );
}
