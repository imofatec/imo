package com.imo.backend.contexts.catalog.lesson.usecases.impl;

import com.imo.backend.contexts.catalog.lesson.Lesson;
import com.imo.backend.contexts.catalog.lesson.LessonPolicies;
import com.imo.backend.contexts.catalog.lesson.actions.commands.UpdateLessonCommand;
import com.imo.backend.contexts.catalog.lesson.http.dtos.UpdateLessonRequest;
import com.imo.backend.contexts.catalog.lesson.repositories.LessonRepository;
import com.imo.backend.contexts.catalog.lesson.usecases.UpdateLessonByIdUseCase;

import org.springframework.stereotype.Service;

@Service
public class UpdateLessonByIdUseCaseImpl implements UpdateLessonByIdUseCase {

  private final LessonRepository lessonRepository;
  private final LessonPolicies lessonPolicies;

  public UpdateLessonByIdUseCaseImpl(
      LessonRepository lessonRepository,
      LessonPolicies lessonPolicies
  ) {
    this.lessonRepository = lessonRepository;
    this.lessonPolicies = lessonPolicies;
  }
  
  @Override
  public Lesson execute(String lessonId, UpdateLessonRequest request) {
    UpdateLessonCommand command = request.toCommand(lessonId);
    return this.execute(command);
  }

  @Override
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
