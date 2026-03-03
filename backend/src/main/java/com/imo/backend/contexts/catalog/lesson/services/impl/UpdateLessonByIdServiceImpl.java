package com.imo.backend.contexts.catalog.lesson.services.impl;

import com.imo.backend.contexts.common.exceptions.custom.ConflictException;
import com.imo.backend.contexts.catalog.lesson.Lesson;
import com.imo.backend.contexts.catalog.lesson.actions.UpdateLessonByIdAction;
import com.imo.backend.contexts.catalog.lesson.actions.inputs.UpdateLessonInput;
import com.imo.backend.contexts.catalog.lesson.guards.GetLessonByIdGuard;
import com.imo.backend.contexts.catalog.lesson.repositories.LessonRepository;
import com.imo.backend.contexts.catalog.lesson.services.UpdateLessonByIdService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UpdateLessonByIdServiceImpl implements UpdateLessonByIdService {
  private final UpdateLessonByIdAction updateLessonByIdAction;

  private final GetLessonByIdGuard getLessonByIdGuard;

  private final LessonRepository lessonRepository;

  public UpdateLessonByIdServiceImpl(
      UpdateLessonByIdAction updateLessonByIdAction,
      GetLessonByIdGuard getLessonByIdGuard,
      LessonRepository lessonRepository
  ) {
    this.updateLessonByIdAction = updateLessonByIdAction;
    this.getLessonByIdGuard = getLessonByIdGuard;
    this.lessonRepository = lessonRepository;
  }

  @Override
  public Lesson execute(String lessonId, UpdateLessonInput input) {
    Lesson foundLesson = this.getLessonByIdGuard.execute(lessonId);
    List<Lesson> lessons = this.lessonRepository.findAllByCourseId(foundLesson.getCourseId());

    lessons.forEach(existingLesson -> {
      if (existingLesson.getId().equals(foundLesson.getId())) {
        return;
      }

      if (existingLesson.getTitle().equals(input.title())) {
        throw new ConflictException("Já existe uma aula com esse título");
      }

      if (existingLesson.getYoutubeLink().equals(input.youtubeLink())) {
        throw new ConflictException("Já existe uma aula com este link de vídeo");
      }
    });

    return this.updateLessonByIdAction.execute(lessonId, input);
  }
}
