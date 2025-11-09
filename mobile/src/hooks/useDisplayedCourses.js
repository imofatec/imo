import { useMemo } from "react";

export function useDisplayedCourses(selectedCategory, progressCourses, userCourses) {
  return useMemo(() => {
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
}
