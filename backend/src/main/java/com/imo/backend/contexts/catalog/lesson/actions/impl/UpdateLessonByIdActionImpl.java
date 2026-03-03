package com.imo.backend.contexts.catalog.lesson.actions.impl;

import com.imo.backend.contexts.common.exceptions.custom.NotFoundException;
import com.imo.backend.contexts.catalog.lesson.Lesson;
import com.imo.backend.contexts.catalog.lesson.actions.UpdateLessonByIdAction;
import com.imo.backend.contexts.catalog.lesson.actions.helpers.LessonUpdater;
import com.imo.backend.contexts.catalog.lesson.actions.inputs.UpdateLessonInput;
import com.imo.backend.contexts.catalog.lesson.repositories.LessonRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdateLessonByIdActionImpl implements UpdateLessonByIdAction {
  private final LessonRepository lessonRepository;

  public UpdateLessonByIdActionImpl(
      LessonRepository lessonRepository
  ) {
    this.lessonRepository = lessonRepository;
  }

  @Override
  public Lesson execute(String id, UpdateLessonInput fieldsToUpdateLesson) {
    Lesson lesson = this.lessonRepository
        .findById(id)
        .orElseThrow(() -> new NotFoundException("Aula não encontrada"));

    LessonUpdater.apply(lesson, fieldsToUpdateLesson);

    return this.lessonRepository.save(lesson);
  }
}
