package com.imo.backend.contexts.catalog.lesson.services;

import com.imo.backend.contexts.catalog.lesson.Lesson;
import com.imo.backend.contexts.catalog.lesson.actions.inputs.CreateLessonInput;

import java.util.List;

public interface CreateLessonService {
  List<Lesson> execute(List<CreateLessonInput> lessonDto, String courseId);

  Lesson execute(CreateLessonInput lessonDto, String courseId);
}
