package com.imo.backend.contexts.catalog.lesson.usecases;

import com.imo.backend.contexts.catalog.lesson.Lesson;
import com.imo.backend.contexts.catalog.lesson.events.FirstLessonInCourseUpdatedEvent;
import com.imo.backend.contexts.catalog.lesson.events.LessonsListUpdatedEvent;
import com.imo.backend.contexts.catalog.lesson.repositories.LessonRepository;
import java.util.List;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class DeleteLessonByIdUseCase {
  private final LessonRepository lessonRepository;

  private final ApplicationEventPublisher applicationEventPublisher;

  public DeleteLessonByIdUseCase(
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
          new FirstLessonInCourseUpdatedEvent(
              foundLesson.getCourseId(),
              (newSequenceOfLessons.isEmpty())
                  ? ""
                  : newSequenceOfLessons.getFirst().getYoutubeLink()));
    }

    this.applicationEventPublisher.publishEvent(
        new LessonsListUpdatedEvent(foundLesson.getCourseId(), newSequenceOfLessons.size()));

    return foundLesson;
  }
}
