package com.imo.backend.modules.course.http.dtos;

import com.imo.backend.modules.course.Course;
import com.imo.backend.modules.course.CourseDetails;
import com.imo.backend.modules.lesson.Lesson;

import java.util.List;

public record CourseDetailsDTO(
    Course course,
    List<Lesson> lessons
) {
  public static CourseDetailsDTO fromCourseDetails(CourseDetails courseDetails) {
    return new CourseDetailsDTO(courseDetails.course(), courseDetails.lessons());
  }
}
