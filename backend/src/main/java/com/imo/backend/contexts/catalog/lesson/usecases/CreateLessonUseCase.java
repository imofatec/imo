package com.imo.backend.contexts.catalog.lesson.usecases;

import com.imo.backend.contexts.catalog.lesson.Lesson;
import com.imo.backend.contexts.catalog.lesson.LessonFactory;
import com.imo.backend.contexts.catalog.lesson.LessonPolicies;
import com.imo.backend.contexts.catalog.lesson.commands.CreateLessonCommand;
import com.imo.backend.contexts.catalog.lesson.events.LessonsListUpdatedEvent;
import com.imo.backend.contexts.catalog.lesson.repositories.LessonRepository;
import java.util.List;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class CreateLessonUseCase {
  private final LessonRepository lessonRepository;
  private final ApplicationEventPublisher applicationEventPublisher;
  private final LessonPolicies lessonPolicies;

  public CreateLessonUseCase(
      LessonRepository lessonRepository,
      ApplicationEventPublisher applicationEventPublisher,
      LessonPolicies lessonPolicies) {
    this.lessonRepository = lessonRepository;
    this.applicationEventPublisher = applicationEventPublisher;
    this.lessonPolicies = lessonPolicies;
  }

  public List<Lesson> execute(List<CreateLessonCommand> commands, String courseId) {
    this.lessonPolicies.checkListInternalConflicts(commands);

    var newLessons = this.lessonRepository.saveAll(LessonFactory.createLesson(commands, courseId));

    this.applicationEventPublisher.publishEvent(
        new LessonsListUpdatedEvent(courseId, newLessons.size()));

    return newLessons;
  }

  public Lesson execute(CreateLessonCommand command, String courseId) {

    this.lessonPolicies.checkLessonConflicts(
        courseId, null, command.title(), command.youtubeLink(), command.description());

    var existingLessonsCount = lessonRepository.findAllByCourseId(courseId).size();

    Lesson newLesson =
        this.lessonRepository.save(
            new Lesson(
                courseId,
                existingLessonsCount + 1,
                command.title(),
                command.description(),
                command.youtubeLink()));

    this.applicationEventPublisher.publishEvent(
        new LessonsListUpdatedEvent(courseId, existingLessonsCount + 1));

    return newLesson;
  }
}
