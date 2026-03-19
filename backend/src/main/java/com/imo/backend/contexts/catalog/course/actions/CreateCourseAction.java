package com.imo.backend.contexts.catalog.course.actions;

import com.imo.backend.contexts.catalog.course.Course;
import com.imo.backend.contexts.catalog.course.actions.commands.CreateCourseCommand;

public interface CreateCourseAction {
  Course execute(CreateCourseCommand command);
}
