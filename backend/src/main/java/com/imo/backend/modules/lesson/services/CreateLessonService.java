package com.imo.backend.modules.lesson.services;

import com.imo.backend.modules.lesson.Lesson;
import com.imo.backend.modules.lesson.actions.inputs.CreateLessonInput;

import java.util.List;

public interface CreateLessonService {
  List<Lesson> execute(List<CreateLessonInput> lessonDto, String courseId);

  Lesson execute(CreateLessonInput lessonDto, String courseId);
}
