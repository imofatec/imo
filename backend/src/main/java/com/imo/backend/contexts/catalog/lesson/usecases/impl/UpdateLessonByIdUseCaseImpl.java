package com.imo.backend.contexts.catalog.lesson.usecases.impl;

import com.imo.backend.contexts.common.exceptions.custom.ConflictException;
import com.imo.backend.contexts.catalog.lesson.Lesson;
import com.imo.backend.contexts.catalog.lesson.actions.commands.UpdateLessonCommand;
import com.imo.backend.contexts.catalog.lesson.http.dtos.UpdateLessonRequest;
import com.imo.backend.contexts.catalog.lesson.repositories.LessonRepository;
import com.imo.backend.contexts.catalog.lesson.usecases.UpdateLessonByIdUseCase;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UpdateLessonByIdUseCaseImpl implements UpdateLessonByIdUseCase {

  private final LessonRepository lessonRepository;

  public UpdateLessonByIdUseCaseImpl(
      LessonRepository lessonRepository
  ) {
    this.lessonRepository = lessonRepository;
  }
  @Override
  public Lesson execute(String lessonId, UpdateLessonRequest request) {
    UpdateLessonCommand command = request.toCommand(lessonId);
    return this.execute(command);
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

    Lesson.applyUpdate(foundLesson, command);
    return this.lessonRepository.save(foundLesson);
  }
}
