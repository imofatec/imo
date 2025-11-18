import React from "react";
import { View } from "react-native";

export default function SkeletonPlayerHeader() {
  return (
    <View className="mb-4 animate-pulse">

      <View className="w-full h-56 bg-gray-700/40 rounded-xl" />

      <View className="px-2 mt-4">

        <View className="w-48 h-6 bg-gray-700/50 rounded mb-3" />

        <View className="w-full h-4 bg-gray-700/40 rounded mb-2" />
        <View className="w-5/6 h-4 bg-gray-700/30 rounded" />

      </View>
    </View>
  );
}
