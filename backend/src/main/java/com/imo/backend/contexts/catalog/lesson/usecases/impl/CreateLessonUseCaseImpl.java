package com.imo.backend.contexts.catalog.lesson.usecases.impl;

import com.imo.backend.contexts.catalog.course.events.IncLessonsCountEvent;
import com.imo.backend.contexts.catalog.course.events.UpdateCourseLessonsCountEvent;
import com.imo.backend.contexts.catalog.lesson.Lesson;
import com.imo.backend.contexts.catalog.lesson.LessonPolicies;
import com.imo.backend.contexts.catalog.lesson.actions.commands.CreateLessonCommand;
import com.imo.backend.contexts.catalog.lesson.actions.factory.LessonFactory;
import com.imo.backend.contexts.catalog.lesson.repositories.LessonRepository;
import com.imo.backend.contexts.catalog.lesson.usecases.CreateLessonUseCase;
import com.imo.backend.contexts.journey_tracking.events.ReevaluateProgressEvent;
import java.util.List;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class CreateLessonUseCaseImpl implements CreateLessonUseCase {
  private final LessonRepository lessonRepository;
  private final ApplicationEventPublisher applicationEventPublisher;
  private final LessonPolicies lessonPolicies;

  public CreateLessonUseCaseImpl(
      LessonRepository lessonRepository,
      ApplicationEventPublisher applicationEventPublisher,
      LessonPolicies lessonPolicies) {
    this.lessonRepository = lessonRepository;
    this.applicationEventPublisher = applicationEventPublisher;
    this.lessonPolicies = lessonPolicies;
  }

  @Override
  public List<Lesson> execute(List<CreateLessonCommand> commands, String courseId) {
    this.lessonPolicies.checkListInternalConflicts(commands);

    var newLessons = this.lessonRepository.saveAll(LessonFactory.createLesson(commands, courseId));

    this.applicationEventPublisher.publishEvent(
        new UpdateCourseLessonsCountEvent(courseId, newLessons.size()));
    this.applicationEventPublisher.publishEvent(new ReevaluateProgressEvent(courseId));

    return newLessons;
  }

  @Override
  public Lesson execute(CreateLessonCommand command, String courseId) {

    this.lessonPolicies.checkLessonConflicts(
        courseId, null, command.title(), command.youtubeLink());

    var existingLessonsCount = lessonRepository.findAllByCourseId(courseId).size();

    Lesson newLesson =
        this.lessonRepository.save(
            LessonFactory.createLesson(command, existingLessonsCount + 1, courseId));

    this.applicationEventPublisher.publishEvent(new IncLessonsCountEvent(courseId));
    this.applicationEventPublisher.publishEvent(new ReevaluateProgressEvent(courseId));

    return newLesson;
  }
}
