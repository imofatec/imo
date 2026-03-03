package com.imo.backend.contexts.catalog.lesson.guards.impl;

import com.imo.backend.contexts.common.exceptions.custom.NotFoundException;
import com.imo.backend.contexts.catalog.lesson.Lesson;
import com.imo.backend.contexts.catalog.lesson.guards.GetLessonByIdGuard;
import com.imo.backend.contexts.catalog.lesson.repositories.LessonRepository;
import org.springframework.stereotype.Service;

@Service
public class GetLessonByIdGuardImpl implements GetLessonByIdGuard {
  private final LessonRepository lessonRepository;

  public GetLessonByIdGuardImpl(LessonRepository lessonRepository) {
    this.lessonRepository = lessonRepository;
  }

  @Override
  public Lesson execute(String lessonId) {
    return this.lessonRepository
        .findById(lessonId)
        .orElseThrow(() -> new NotFoundException("Aula não encontrada"));
  }
}
