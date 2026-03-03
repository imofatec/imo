package com.imo.backend.contexts.catalog.course.guards;

import com.imo.backend.contexts.catalog.course.Course;

public interface GetCourseByLessonIdGuard {
  Course execute(String lessonId);
}
