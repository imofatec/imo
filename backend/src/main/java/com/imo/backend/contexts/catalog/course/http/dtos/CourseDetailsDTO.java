package com.imo.backend.contexts.catalog.course.http.dtos;

import com.imo.backend.contexts.catalog.course.CourseDetails;
import com.imo.backend.contexts.catalog.lesson.Lesson;
import java.util.List;

public record CourseDetailsDTO(CourseDTO course, List<Lesson> lessons) {
  public static CourseDetailsDTO fromCourseDetails(CourseDetails courseDetails) {
    return new CourseDetailsDTO(
        CourseDTO.fromEntity(courseDetails.course()), courseDetails.lessons());
  }
}
