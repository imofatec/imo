import { useLocalSearchParams, router } from "expo-router";
import React, { useState, useEffect } from "react";
import { View, Text, FlatList } from "react-native";
import { useAllCourses } from "../../hooks/useAllCourses";
import CourseCard from "../../components/allCourses/courseCard";
import SkeletonCourseCard from "../../components/skeletonScreens/skeletonCourseCard";
import FilterDrawer from "../../components/allCourses/filterDrawer";
import PaginationControls from "../../components/allCourses/paginationControls";

export default function AllCourses() {
  const [selectedCategory, setSelectedCategory] = useState(null);
  const [page, setPage] = useState(0);
  const size = 10;
  const params = useLocalSearchParams();
  const searchTerm = params.search || "";

  const nameToFetch = selectedCategory ? undefined : searchTerm;

  const { courses, loading, error, refetch } = useAllCourses({ page, size, categorySlug: selectedCategory || undefined, name: nameToFetch, matchType: nameToFetch ? "CONTAINS" : "PERFECT", });

  useEffect(() => {
    setPage(0);
  }, [selectedCategory, searchTerm]);

  useEffect(() => {
    if (selectedCategory && searchTerm) {
      router.setParams({ search: undefined });
    }
  }, [selectedCategory]);

  useEffect(() => {
    if (searchTerm && selectedCategory) {
      setSelectedCategory(null);
    }
  }, [searchTerm]);

  const getTitle = () => {
    if (selectedCategory) return selectedCategory;
    if (searchTerm) return `Resultados para "${searchTerm}"`;
    return "Todos os Cursos";
  };

  if (error) {
    return (
      <View className="flex-1 bg-custom-primary justify-center items-center">
        <Text className="text-red-500">{error}</Text>
      </View>
    );
  }

  const isPrevDisabled = page === 0;
  const isNextDisabled = courses.length < size;


  return (
    <View className="flex-1 bg-custom-primary">
      <View className="flex-row items-center justify-between px-4 py-4">
        <Text className="text-white text-xl font-bold pl-3">
          {getTitle()}
        </Text>
        <FilterDrawer selectedCategory={selectedCategory} setSelectedCategory={setSelectedCategory} />
      </View>

      {loading && (
        <FlatList
          data={Array.from({ length: size })}
          keyExtractor={(_, index) => index.toString()}
          renderItem={() => <SkeletonCourseCard />}
          numColumns={2}
          columnWrapperStyle={{
            justifyContent: "space-between",
            paddingHorizontal: 16,
          }}
          contentContainerStyle={{ paddingBottom: 16 }}
        />
      )}

      {!loading && !error && (
        <FlatList
          data={courses}
          keyExtractor={(item) => item.id}
          renderItem={({ item }) => (
            <CourseCard
              nome={item.name.name}
              courseID={item.id}
              nomeSlug={item.name.slug}
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
