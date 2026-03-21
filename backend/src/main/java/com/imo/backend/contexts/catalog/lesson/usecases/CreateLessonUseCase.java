package com.imo.backend.contexts.catalog.lesson.usecases;

import com.imo.backend.contexts.catalog.lesson.Lesson;
import com.imo.backend.contexts.catalog.lesson.actions.commands.CreateLessonCommand;
import java.util.List;

public interface CreateLessonUseCase {
  List<Lesson> execute(List<CreateLessonCommand> command, String courseId);

  Lesson execute(CreateLessonCommand command, String courseId);
}
