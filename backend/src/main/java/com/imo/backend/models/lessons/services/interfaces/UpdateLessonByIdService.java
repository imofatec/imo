package com.imo.backend.models.lessons.services.interfaces;

import com.imo.backend.models.course.dtos.FieldsToUpdateLesson;
import com.imo.backend.models.lessons.dtos.NoCommentsLesson;

public interface UpdateLessonByIdService {
  NoCommentsLesson execute(String lessonId, FieldsToUpdateLesson fieldsToUpdateLesson);
}
