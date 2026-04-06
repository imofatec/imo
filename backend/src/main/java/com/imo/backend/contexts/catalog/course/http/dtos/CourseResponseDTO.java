package com.imo.backend.contexts.catalog.course.http.dtos;

import com.imo.backend.contexts.catalog.course.Course;
import com.imo.backend.contexts.catalog.course.value_objects.Category;
import com.imo.backend.contexts.catalog.course.value_objects.CourseName;
import com.imo.backend.contexts.catalog.course.value_objects.Level;

public record CourseResponseDTO(
    String id,
    CourseName name,
    Level level,
    Category category,
    String description,
    String firstLessonYoutubeLink,
    int lessonsCount,
    boolean isActive) {
  public static CourseResponseDTO fromEntity(Course course) {
    return new CourseResponseDTO(
        course.getId(),
        course.getName(),
        course.getLevel(),
        course.getCategory(),
        course.getDescription(),
        course.getFirstLessonYoutubeLink(),
        course.getLessonsCount(),
        course.isActive());
  }
}
