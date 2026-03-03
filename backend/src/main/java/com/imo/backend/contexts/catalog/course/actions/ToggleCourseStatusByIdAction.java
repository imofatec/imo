package com.imo.backend.contexts.catalog.course.actions;

import com.imo.backend.contexts.catalog.course.Course;

public interface ToggleCourseStatusByIdAction {
  Course execute(String id);
}