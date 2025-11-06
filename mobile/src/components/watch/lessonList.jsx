import React from "react";
import { View } from "react-native";
import LessonCard from "./lessonCard";

export default function LessonList({ lessons, currentId, onSelect, watchedSet, onToggleWatched,disabledLessons = new Set(), }) {
  return (
    <View className="px-2">
      {lessons.map((lesson) => (
        <LessonCard
          key={lesson.id}
          lesson={lesson}
          isActive={lesson.id === currentId}
          onPress={() => onSelect(lesson)}
          watched={watchedSet.has(lesson.id)}
          onToggleWatched={onToggleWatched}
          disabled={disabledLessons.has(lesson.id)}
        />
      ))}
    </View>
  );
}
