package com.imo.backend.contexts.catalog.course.actions;

import com.imo.backend.contexts.catalog.course.Course;
import com.imo.backend.contexts.catalog.course.actions.commands.UpdateCourseByIdCommand;

public interface UpdateCourseByIdAction {
  Course execute(UpdateCourseByIdCommand command);
}
