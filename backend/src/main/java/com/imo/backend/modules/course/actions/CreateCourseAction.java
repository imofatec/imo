package com.imo.backend.modules.course.actions;

import com.imo.backend.modules.course.Course;
import com.imo.backend.modules.course.actions.inputs.CreateCourseInput;

public interface CreateCourseAction {
  Course execute(CreateCourseInput input);
}
