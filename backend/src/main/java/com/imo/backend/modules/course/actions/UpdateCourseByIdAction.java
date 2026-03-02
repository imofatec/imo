package com.imo.backend.modules.course.actions;

import com.imo.backend.modules.course.Course;
import com.imo.backend.modules.course.actions.inputs.UpdateCourseByIdInput;

public interface UpdateCourseByIdAction {
  Course execute(String courseId, UpdateCourseByIdInput fieldsToUpdateCourse);
}
