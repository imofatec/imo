import React, { useState, useEffect } from "react";
import { View, Text, FlatList } from "react-native";
import { useAllCourses } from "../../hooks/useAllCourses";
import { useCourseProgress } from "../../hooks/useCourseProgress";
import { useCurrentUser } from "../../hooks/useCurrentUser";
import { useDisplayedCourses } from "../../hooks/useDisplayedCourses";
import FilterDrawer from "../../components/myCourses/filterDrawer";
import CourseCard from "../../components/allCourses/courseCard";
import PaginationControls from "../../components/allCourses/paginationControls";
import EmptyCoursesMessage from "../../components/myCourses/emptyCoursesMessage";

export default function AllCourses() {
  const [selectedCategory, setSelectedCategory] = useState(null);
  const [page, setPage] = useState(0);
  const size = 10;

  const { user, error: userError } = useCurrentUser();
  const { courses: progressCourses, loading: progressLoading, error: progressError, refetch: refetchProgress } = useCourseProgress(page, size);
  const { courses: userCourses, loading: userCoursesLoading, error: userCoursesError, refetch: refetchUserCourses } =
    useAllCourses({ page, size, contributorId: user ? user.id : null });

  const displayedCourses = useDisplayedCourses(selectedCategory, progressCourses, userCourses);

  const loading = progressLoading || userCoursesLoading;
  const error = progressError || userCoursesError || userError;

  useEffect(() => setPage(0), [selectedCategory]);

  const getTitle = () => {
    switch (selectedCategory) {
      case "in_progress": return "Em Andamento";
      case "finished": return "Finalizados";
      case "contributions": return "Minhas Contribuições";
      default: return "Meus Cursos";
    }
  };

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
        <Text className="text-white text-xl font-bold pl-3">{getTitle()}</Text>
        <FilterDrawer selectedCategory={selectedCategory} setSelectedCategory={setSelectedCategory} />
      </View>

      {loading && <Text className="text-white mt-2">Carregando Cursos...</Text>}

      {!loading && !error && displayedCourses.length > 0 ? (
        <FlatList
          data={displayedCourses}
          keyExtractor={(item) => item.course ? item.course.id : item.id}
          renderItem={({ item }) => {
            const course = item.course || item;
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
          columnWrapperStyle={{ justifyContent: "space-between", paddingHorizontal: 16 }}
          contentContainerStyle={{ paddingBottom: 16 }}
          refreshing={loading}
          onRefresh={handleRefresh}
        />
      ) : (
        !loading && !error && <EmptyCoursesMessage selectedCategory={selectedCategory} />
      )}

      <PaginationControls
        page={page}
        onPrev={() => setPage((prev) => prev - 1)}
        onNext={() => setPage((prev) => prev + 1)}
        isPrevDisabled={page === 0}
        isNextDisabled={!displayedCourses || displayedCourses.length < size}
      />
    </View>
  );
}