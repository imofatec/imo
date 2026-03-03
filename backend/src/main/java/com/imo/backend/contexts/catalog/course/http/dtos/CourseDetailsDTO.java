package com.imo.backend.contexts.catalog.course.http.dtos;

import com.imo.backend.contexts.catalog.course.Course;
import com.imo.backend.contexts.catalog.course.CourseDetails;
import com.imo.backend.contexts.catalog.lesson.Lesson;

import java.util.List;

public record CourseDetailsDTO(
    Course course,
    List<Lesson> lessons
) {
  public static CourseDetailsDTO fromCourseDetails(CourseDetails courseDetails) {
    return new CourseDetailsDTO(courseDetails.course(), courseDetails.lessons());
  }
}
