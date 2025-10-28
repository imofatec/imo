import React, { useState, useEffect } from "react";
import { View, Text, FlatList, ActivityIndicator, Pressable } from "react-native";
import { useAllCourses } from "../../hooks/useAllCourses";
import CourseCard from "../../components/allCourses/courseCard";
import FilterDrawer from "../../components/allCourses/filterDrawer";
import PaginationControls from "../../components/allCourses/paginationControls";

export default function AllCourses() {
  const [selectedCategory, setSelectedCategory] = useState(null);
  const [page, setPage] = useState(0);
  const size = 10;
  const { courses, loading, error, refetch } = useAllCourses({ page, size, categorySlug: selectedCategory || undefined });

  useEffect(() => {
    setPage(0);
  }, [selectedCategory]);

  if (error) return <Text style={{ color: "red" }}>{error}</Text>;

  const isPrevDisabled = page === 0;
  const isNextDisabled = courses.length < size;


  return (
    <View className="flex-1 bg-custom-primary">
      <View className="flex-row items-center justify-between px-4 py-4">
        <Text className="text-white text-xl font-bold pl-3">{selectedCategory || "Todos os Cursos"}</Text>
        <FilterDrawer selectedCategory={selectedCategory} setSelectedCategory={setSelectedCategory} />
      </View>

      {loading && <Text className="text-white mt-2">Carregando categorias...</Text>}

      {!loading && !error && (
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
      )
      }
      <PaginationControls page={page} onPrev={() => setPage(prev => prev - 1)} onNext={() => setPage(prev => prev + 1)} isPrevDisabled={isPrevDisabled} isNextDisabled={isNextDisabled} />
    </View>
  );
}
