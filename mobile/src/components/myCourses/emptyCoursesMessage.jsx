import React from "react";
import { View, Text } from "react-native";

export default function EmptyCoursesMessage({ selectedCategory }) {
  const getEmptyMessage = () => {
    switch (selectedCategory) {
      case "in_progress":
        return "Parece que você ainda não começou nenhum curso.";
      case "finished":
        return "Parece que você ainda não finalizou nenhum curso.";
      case "contributions":
        return "Você ainda não publicou nenhum curso.";
      default:
        return "Você ainda não possui cursos. Busque por cursos em nossa plataforma!";
    }
  };

  return (
    <View className="flex-1 justify-center items-center px-6">
      <Text className="text-white text-center text-base mt-10">
        {getEmptyMessage()}
      </Text>
    </View>
  );
}
