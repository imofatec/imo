package com.imo.backend.contexts.catalog.lesson.actions.impl;

import com.imo.backend.contexts.common.exceptions.custom.NotFoundException;
import com.imo.backend.contexts.catalog.lesson.Lesson;
import com.imo.backend.contexts.catalog.lesson.actions.UpdateLessonByIdAction;
import com.imo.backend.contexts.catalog.lesson.actions.commands.UpdateLessonCommand;
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
  public Lesson execute(UpdateLessonCommand command) {
    Lesson lesson = this.lessonRepository
        .findById(command.lessonId())
        .orElseThrow(() -> new NotFoundException("Aula não encontrada"));

    Lesson.applyUpdate(lesson, command);

    return this.lessonRepository.save(lesson);
  }
}
