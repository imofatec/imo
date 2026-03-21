package com.imo.backend.contexts.catalog.lesson.usecases.impl;

import com.imo.backend.contexts.common.exceptions.custom.ConflictException;
import com.imo.backend.contexts.catalog.lesson.Lesson;
import com.imo.backend.contexts.catalog.lesson.actions.UpdateLessonByIdAction;
import com.imo.backend.contexts.catalog.lesson.actions.commands.UpdateLessonCommand;
import com.imo.backend.contexts.catalog.lesson.repositories.LessonRepository;
import com.imo.backend.contexts.catalog.lesson.usecases.UpdateLessonByIdUseCase;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UpdateLessonByIdUseCaseImpl implements UpdateLessonByIdUseCase {
  private final UpdateLessonByIdAction updateLessonByIdAction;

  private final LessonRepository lessonRepository;

  public UpdateLessonByIdUseCaseImpl(
      UpdateLessonByIdAction updateLessonByIdAction,
      LessonRepository lessonRepository
  ) {
    this.updateLessonByIdAction = updateLessonByIdAction;
    this.lessonRepository = lessonRepository;
  }

  @Override
  public Lesson execute(UpdateLessonCommand command) {
    Lesson foundLesson = this.lessonRepository.findByIdOrThrow(command.lessonId());
    List<Lesson> lessons = this.lessonRepository.findAllByCourseId(foundLesson.getCourseId());

    lessons.forEach(existingLesson -> {
      if (existingLesson.getId().equals(foundLesson.getId())) {
        return;
      }

      if (existingLesson.getTitle().equals(command.title())) {
        throw new ConflictException("Já existe uma aula com esse título");
      }

      if (existingLesson.getYoutubeLink().equals(command.youtubeLink())) {
        throw new ConflictException("Já existe uma aula com este link de vídeo");
      }
    });

    return this.updateLessonByIdAction.execute(command);
  }
}
