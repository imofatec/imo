package com.imo.backend.modules.course.guards;

import com.imo.backend.modules.course.Course;

public interface GetCourseByLessonIdGuard {
  Course execute(String lessonId);
}
