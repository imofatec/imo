package com.imo.backend.contexts.catalog.lesson.usecases;

import com.imo.backend.contexts.catalog.lesson.Lesson;
import com.imo.backend.contexts.catalog.lesson.LessonPolicies;
import com.imo.backend.contexts.catalog.lesson.commands.UpdateLessonCommand;
import com.imo.backend.contexts.catalog.lesson.repositories.LessonRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdateLessonByIdUseCase {

  private final LessonRepository lessonRepository;
  private final LessonPolicies lessonPolicies;

  public UpdateLessonByIdUseCase(LessonRepository lessonRepository, LessonPolicies lessonPolicies) {
    this.lessonRepository = lessonRepository;
    this.lessonPolicies = lessonPolicies;
  }

  public Lesson execute(UpdateLessonCommand command) {
    Lesson foundLesson = this.lessonRepository.findByIdOrThrow(command.lessonId());

    this.lessonPolicies.checkLessonConflicts(
        foundLesson.getCourseId(),
        foundLesson.getId(),
        command.title(),
        command.youtubeLink()
    );

    Lesson.applyUpdate(foundLesson, command);
    return this.lessonRepository.save(foundLesson);
  }
}
