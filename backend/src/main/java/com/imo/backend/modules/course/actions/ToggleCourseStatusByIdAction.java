package com.imo.backend.modules.course.actions;

import com.imo.backend.modules.course.Course;

public interface ToggleCourseStatusByIdAction {
  Course execute(String id);
}