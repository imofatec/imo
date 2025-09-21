package com.imo.backend.modules.lesson.services.impl;

import com.imo.backend.modules.course.events.UpdateCourseFirstYoutubeLinkEvent;
import com.imo.backend.modules.course.events.UpdateCourseLessonsCountEvent;
import com.imo.backend.modules.lesson.Lesson;
import com.imo.backend.modules.lesson.guards.GetLessonByIdGuard;
import com.imo.backend.modules.lesson.repositories.LessonRepository;
import com.imo.backend.modules.lesson.services.DeleteLessonByIdService;
import com.imo.backend.modules.progress.events.ReevaluateProgressEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeleteLessonByIdServiceImpl implements DeleteLessonByIdService {
  private final GetLessonByIdGuard getLessonByIdGuard;

  private final LessonRepository lessonRepository;

  private final ApplicationEventPublisher applicationEventPublisher;

  public DeleteLessonByIdServiceImpl(
      GetLessonByIdGuard getLessonByIdGuard,
      LessonRepository lessonRepository,
      ApplicationEventPublisher applicationEventPublisher
  ) {
    this.getLessonByIdGuard = getLessonByIdGuard;
    this.lessonRepository = lessonRepository;
    this.applicationEventPublisher = applicationEventPublisher;
  }

  public Lesson execute(String lessonId) {
    Lesson foundLesson = this.getLessonByIdGuard.execute(lessonId);

    this.lessonRepository.deleteById(lessonId);

    List<Lesson> formattedLessons = Lesson.reindexLessons(this.lessonRepository.findAllByCourseId(
        foundLesson.getCourseId()));

    var newSequenceOfLessons = this.lessonRepository.saveAll(formattedLessons);

    Lesson.sortLessonsByIndexInCourse(newSequenceOfLessons);

    if (foundLesson.getIndexInCourse() == 1) {
      this.applicationEventPublisher.publishEvent(new UpdateCourseFirstYoutubeLinkEvent(
          foundLesson.getCourseId(),
          (newSequenceOfLessons.isEmpty()) ? "" : newSequenceOfLessons.getFirst().getYoutubeLink()
      ));
    }

    this.applicationEventPublisher.publishEvent(new UpdateCourseLessonsCountEvent(
        foundLesson.getCourseId(),
        newSequenceOfLessons.size()
    ));

    this.applicationEventPublisher.publishEvent(new ReevaluateProgressEvent(foundLesson.getCourseId()));

    return foundLesson;
  }

}
