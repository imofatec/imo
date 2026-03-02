import React from "react";
import { View } from "react-native";

export default function SkeletonLessonCard() {
  return (
    <View className="mb-3 p-4 rounded-2xl bg-white/5 animate-pulse">
      <View className="flex-row justify-between items-start">
        
        <View className="flex-1 pr-3">
          <View className="w-40 h-5 bg-gray-700/50 rounded" />
          <View className="w-28 h-4 bg-gray-700/40 rounded mt-2" />
        </View>

        <View className="items-end">
          <View className="px-3 py-1 rounded-full bg-gray-700/40 mb-2">
            <View className="w-12 h-4 bg-gray-600/40 rounded" />
          </View>
          <View className="w-8 h-8 bg-gray-700/30 rounded-full" />
        </View>

      </View>
    </View>
  );
}
