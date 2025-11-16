import React from "react";
import { View, Text, Pressable } from "react-native";
export default function PaginationControls({ page, onPrev, onNext, isPrevDisabled, isNextDisabled }) {
  return (
    <View className="flex-row justify-center items-center py-4">
      <Pressable onPress={onPrev} disabled={isPrevDisabled} className="px-4 py-2 rounded-l">
        <Text className={`${isPrevDisabled ? 'text-gray-500' : 'text-white'}`}>{'<'}</Text>
      </Pressable>
      <Text className="text-white text-xl px-6">{page + 1}</Text>
      <Pressable onPress={onNext} disabled={isNextDisabled} className="px-4 py-2 rounded-r">
        <Text className={`${isNextDisabled ? 'text-gray-500' : 'text-white'}`}>{'>'}</Text>
      </Pressable>
    </View>
  );
}
