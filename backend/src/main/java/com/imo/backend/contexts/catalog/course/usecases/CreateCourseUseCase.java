package com.imo.backend.contexts.catalog.course.usecases;

import com.imo.backend.contexts.catalog.course.Course;
import com.imo.backend.contexts.catalog.course.actions.commands.CreateCourseCommand;

public interface CreateCourseUseCase {
  Course execute(CreateCourseCommand command);
}
