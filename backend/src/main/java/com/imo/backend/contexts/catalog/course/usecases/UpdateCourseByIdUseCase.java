package com.imo.backend.contexts.catalog.course.usecases;

import com.imo.backend.contexts.catalog.course.Course;
import com.imo.backend.contexts.catalog.course.actions.commands.UpdateCourseByIdCommand;
import com.imo.backend.contexts.catalog.course.http.dtos.UpdateCourseByIdRequest;

public interface UpdateCourseByIdUseCase {
  Course execute(String courseId, UpdateCourseByIdRequest fieldsToUpdateCourse);

  Course execute(UpdateCourseByIdCommand command);
}
