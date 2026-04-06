package com.imo.backend.contexts.catalog.course.usecases;

import com.imo.backend.contexts.catalog.course.Course;

public interface ToggleCourseStatusByIdUseCase {
  Course execute(String id);
}
