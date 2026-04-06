package com.imo.backend.contexts.catalog.lesson.usecases.impl;

import com.imo.backend.contexts.catalog.course.events.UpdateCourseFirstYoutubeLinkEvent;
import com.imo.backend.contexts.catalog.course.events.UpdateCourseLessonsCountEvent;
import com.imo.backend.contexts.catalog.lesson.Lesson;
import com.imo.backend.contexts.catalog.lesson.repositories.LessonRepository;
import com.imo.backend.contexts.catalog.lesson.usecases.DeleteLessonByIdUseCase;
import com.imo.backend.contexts.journey_tracking.events.ReevaluateProgressEvent;
import java.util.List;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class DeleteLessonByIdUseCaseImpl implements DeleteLessonByIdUseCase {
  private final LessonRepository lessonRepository;

  private final ApplicationEventPublisher applicationEventPublisher;

  public DeleteLessonByIdUseCaseImpl(
      LessonRepository lessonRepository, ApplicationEventPublisher applicationEventPublisher) {
    this.lessonRepository = lessonRepository;
    this.applicationEventPublisher = applicationEventPublisher;
  }

  public Lesson execute(String lessonId) {
    Lesson foundLesson = this.lessonRepository.findByIdOrThrow(lessonId);

    this.lessonRepository.deleteById(lessonId);

    List<Lesson> formattedLessons =
        Lesson.reindexLessons(this.lessonRepository.findAllByCourseId(foundLesson.getCourseId()));

    var newSequenceOfLessons = this.lessonRepository.saveAll(formattedLessons);

    Lesson.sortLessonsByIndexInCourse(newSequenceOfLessons);

    if (foundLesson.getIndexInCourse() == 1) {
      this.applicationEventPublisher.publishEvent(
          new UpdateCourseFirstYoutubeLinkEvent(
              foundLesson.getCourseId(),
              (newSequenceOfLessons.isEmpty())
                  ? ""
                  : newSequenceOfLessons.getFirst().getYoutubeLink()));
    }

    this.applicationEventPublisher.publishEvent(
        new UpdateCourseLessonsCountEvent(foundLesson.getCourseId(), newSequenceOfLessons.size()));

    this.applicationEventPublisher.publishEvent(
        new ReevaluateProgressEvent(foundLesson.getCourseId()));

    return foundLesson;
  }
}
