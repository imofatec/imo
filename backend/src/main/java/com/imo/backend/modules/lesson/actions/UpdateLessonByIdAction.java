package com.imo.backend.modules.lesson.actions;

import com.imo.backend.modules.lesson.Lesson;
import com.imo.backend.modules.lesson.actions.inputs.UpdateLessonInput;

public interface UpdateLessonByIdAction {
  Lesson execute(String id, UpdateLessonInput fieldsToUpdateLesson);
}
