import React, { useState, useEffect, useMemo } from "react";
import { View, Text, FlatList } from "react-native";
import { useAllCourses } from "../../hooks/useAllCourses";
import CourseCard from "../../components/allCourses/courseCard";
import FilterDrawer from "../../components/myCourses/filterDrawer";
import PaginationControls from "../../components/allCourses/paginationControls";
import { useCurrentUser } from "../../hooks/useCurrentUser";
import { useCourseProgress } from "../../hooks/useCourseProgress";

export default function AllCourses() {
  const [selectedCategory, setSelectedCategory] = useState(null);
  const [page, setPage] = useState(0);
  const size = 10;
  const { user, loading: userLoading, error: userError } = useCurrentUser();

  const { courses: progressCourses, loading: progressLoading, error: progressError, refetch: refetchProgress, } = useCourseProgress(page, size);

  const { courses: userCourses, loading: userCoursesLoading, error: userCoursesError, refetch: refetchUserCourses, } = useAllCourses({ page, size, contributorId: user ? user.id : null });

  useEffect(() => {
    setPage(0);
  }, [selectedCategory]);

  const displayedCourses = useMemo(() => {
    if (!progressCourses && !userCourses) return [];

    let result = [];

    switch (selectedCategory) {
      case "in_progress":
        result = progressCourses?.filter(
          (item) => item.progress?.status === "IN_PROGRESS"
        );
        break;
      case "finished":
        result = progressCourses?.filter(
          (item) => item.progress?.status === "FINISHED"
        );
        break;
      case "contributions":
        result = userCourses;
        break;
      default:
        result = [...(progressCourses || []), ...(userCourses || [])];
        break;
    }
    const unique = new Map();
    result?.forEach((item) => {
      const courseId = item.course ? item.course.id : item.id;
      if (!unique.has(courseId)) {
        unique.set(courseId, item);
      }
    });

    return Array.from(unique.values());
  }, [selectedCategory, progressCourses, userCourses]);

  const loading = progressLoading || userCoursesLoading;
  const error = progressError || userCoursesError || userError;

  const getTitle = () => {
    switch (selectedCategory) {
      case "in_progress":
        return "Em Andamento";
      case "finished":
        return "Finalizados";
      case "contributions":
        return "Minhas Contribuições";
      default:
        return "Meus Cursos";
    }
  };

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

  const isPrevDisabled = page === 0;
  const isNextDisabled = !displayedCourses || displayedCourses.length < size;

  const handleRefresh = () => {
    refetchProgress();
    refetchUserCourses();
  };

  if (error) {
    return (
      <View className="flex-1 bg-custom-primary justify-center items-center">
        <Text className="text-red-500">{error}</Text>
      </View>
    );
  }

  return (
    <View className="flex-1 bg-custom-primary">
      <View className="flex-row items-center justify-between px-4 py-4">
        <Text className="text-white text-xl font-bold pl-3">
          {getTitle()}
        </Text>
        <FilterDrawer selectedCategory={selectedCategory} setSelectedCategory={setSelectedCategory} />
      </View>

      {loading && <Text className="text-white mt-2">Carregando Cursos...</Text>}

      {!loading && !error && displayedCourses.length === 0 && (
        <View className="flex-1 justify-center items-center px-6">
          <Text className="text-white text-center text-base mt-10">
            {getEmptyMessage()}
          </Text>
        </View>
      )}

      {!loading && !error && displayedCourses.length > 0 && (
        <FlatList
          data={displayedCourses}
          keyExtractor={(item) => item.course ? item.course.id : item.id}
          renderItem={({ item }) => {
            const course = item.course ? item.course : item;
            return (
              <CourseCard
                nome={course.name.name}
                courseID={course.id}
                nomeSlug={course.name.slug}
                idImg={course.firstLessonYoutubeLink}
                descricao={course.description}
              />
            );
          }}
          numColumns={2}
          columnWrapperStyle={{
            justifyContent: "space-between",
            paddingHorizontal: 16,
          }}
          contentContainerStyle={{ paddingBottom: 16 }}
          refreshing={loading}
          onRefresh={handleRefresh}
        />
      )
      }
      <PaginationControls page={page} onPrev={() => setPage(prev => prev - 1)} onNext={() => setPage(prev => prev + 1)} isPrevDisabled={isPrevDisabled} isNextDisabled={isNextDisabled} />

    </View>
  );
}
