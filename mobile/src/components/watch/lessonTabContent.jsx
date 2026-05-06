import React from "react";
import { View, Text } from "react-native";
import LessonList from "./lessonList";
import SkeletonLessonProgress from "../skeletonScreens/skeletonLessonProgress";
import SkeletonLessonList from "../skeletonScreens/skeletonLessonList";

export default function LessonTabContent({
  lessons,
  isloading,
  currentLesson,
  watched,
  watchedCount,
  progressPercent,
  onSelectLesson,
  onToggleWatched,
  disabledLessons
}) {
  return (
    <>
      {isloading ? (
        <>
          <SkeletonLessonProgress />
          <SkeletonLessonList />
        </>
      ) : (
        <>
          <View className="mt-4 px-2">
            <Text className="text-white font-medium mb-2">
              Progresso: {watchedCount} / {lessons.length}
            </Text>
            <View className="w-full h-3 bg-white/10 rounded-full overflow-hidden">
              <View className="h-full bg-white" style={{ width: `${progressPercent}%` }} />
            </View>
          </View>

          <View className="mt-6">
            <Text className="text-white text-xl font-bold mb-3">Aulas do Curso</Text>
            <LessonList
              lessons={lessons}
              currentId={currentLesson?.id}
              onSelect={onSelectLesson}
              watchedSet={watched}
              onToggleWatched={onToggleWatched}
              disabledLessons={disabledLessons}
            />
          </View>
        </>
      )}
    </>
  );
}
