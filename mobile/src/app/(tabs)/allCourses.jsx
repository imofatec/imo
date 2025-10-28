import React from "react";
import { View, Text, FlatList, ActivityIndicator } from "react-native";
import { useAllCourses } from "../../hooks/useAllCourses";
import CourseCard from "../../components/allCourses/courseCard";

export default function AllCourses() {
  const { courses, loading, error, refetch } = useAllCourses({});

  if (loading) return <ActivityIndicator size="large" color="#000"/>;
  if (error) return <Text style={{ color: "red" }}>{error}</Text>;

  return (
    <View className="flex-1 bg-custom-primary">
      <Text className="text-white text-xl text-center py-4">Cursos</Text>

      <FlatList
        data={courses}
        keyExtractor={(item) => item.id}
        renderItem={({ item }) => (
          <CourseCard
            nome={item.name.name}
            idImg={item.firstLessonYoutubeLink}
            descricao={item.description}
          />
        )}
        numColumns={2}
        columnWrapperStyle={{
          justifyContent: "space-between",
          paddingHorizontal: 16,
        }}
        contentContainerStyle={{ paddingBottom: 16 }}
        refreshing={loading}
        onRefresh={refetch}
      />
      <Text className="text-white text-xl text-center py-4">{`<        1        >`}</Text>
    </View>
  );
}
