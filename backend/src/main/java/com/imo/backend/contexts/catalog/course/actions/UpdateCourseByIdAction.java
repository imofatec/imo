package com.imo.backend.contexts.catalog.course.actions;

import com.imo.backend.contexts.catalog.course.Course;
import com.imo.backend.contexts.catalog.course.actions.inputs.UpdateCourseByIdInput;

public interface UpdateCourseByIdAction {
  Course execute(String courseId, UpdateCourseByIdInput fieldsToUpdateCourse);
}
