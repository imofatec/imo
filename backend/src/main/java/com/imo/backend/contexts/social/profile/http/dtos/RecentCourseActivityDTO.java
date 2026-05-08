package com.imo.backend.contexts.social.profile.http.dtos;

import com.imo.backend.contexts.social.profile.RecentCourseActivityDetails;

public record RecentCourseActivityDTO(
    String courseId,
    String courseName,
    String firstLessonYoutubeLink,
    int watchedLessonsCount,
    int totalLessonsCount) {
  public static RecentCourseActivityDTO fromDetails(RecentCourseActivityDetails course) {
    return new RecentCourseActivityDTO(
        course.courseId(),
        course.courseName(),
        course.firstLessonYoutubeLink(),
        course.watchedLessonsCount(),
        course.totalLessonsCount());
  }
}
