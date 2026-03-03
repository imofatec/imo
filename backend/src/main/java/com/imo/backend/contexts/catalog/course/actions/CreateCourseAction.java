package com.imo.backend.contexts.catalog.course.actions;

import com.imo.backend.contexts.catalog.course.Course;
import com.imo.backend.contexts.catalog.course.actions.inputs.CreateCourseInput;

public interface CreateCourseAction {
  Course execute(CreateCourseInput input);
}
