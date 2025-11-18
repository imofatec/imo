import React from "react";
import { View } from "react-native";
import SkeletonLessonCard from "./skeletonLessonCard";

export default function SkeletonLessonList() {
  return (
    <View className="px-2 mt-4">
      {Array.from({ length: 6 }).map((_, i) => (
        <SkeletonLessonCard key={i} />
      ))}
    </View>
  );
}
