package com.imo.backend.contexts.catalog.course.http.dtos;

import com.imo.backend.contexts.catalog.course.Category;
import com.imo.backend.contexts.catalog.course.Course;
import com.imo.backend.contexts.catalog.course.CourseName;
import com.imo.backend.contexts.catalog.course.Level;
import java.util.List;

public record CourseDTO(
    String id,
    CourseName name,
    Level level,
    Category category,
    String description,
    String firstLessonYoutubeLink,
    int lessonsCount,
    List<String> skillIds,
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
        course.getSkillIdsAsString(),
        course.isActive());
  }
}
