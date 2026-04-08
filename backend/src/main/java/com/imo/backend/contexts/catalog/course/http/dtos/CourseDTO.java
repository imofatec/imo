package com.imo.backend.contexts.catalog.course.http.dtos;

import com.imo.backend.contexts.catalog.course.Course;
import com.imo.backend.contexts.catalog.course.value_objects.Category;
import com.imo.backend.contexts.catalog.course.value_objects.CourseName;
import com.imo.backend.contexts.catalog.course.value_objects.Level;

public record CourseDTO(
    String id,
    CourseName name,
    Level level,
    Category category,
    String description,
    String firstLessonYoutubeLink,
    int lessonsCount,
    boolean isActive) {
  public static CourseDTO fromEntity(Course course) {
    return new CourseDTO(
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
