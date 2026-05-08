package com.imo.backend.contexts.catalog.lesson.http.dtos;

import com.imo.backend.contexts.catalog.lesson.Lesson;

public record LessonDTO(
    String id,
    String title,
    String description,
    String youtubeLink,
    int indexInCourse,
    String courseId) {
  public static LessonDTO fromEntity(Lesson lesson) {
    return new LessonDTO(
        lesson.getId(),
        lesson.getTitle(),
        lesson.getDescription(),
        lesson.getYoutubeLink(),
        lesson.getIndexInCourse(),
        lesson.getCourseId());
  }
}
