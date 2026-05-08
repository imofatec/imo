package com.imo.backend.contexts.catalog.course.events;

import com.imo.backend.contexts.catalog.course.commands.UpdateCourseByIdCommand;
import com.imo.backend.contexts.catalog.course.usecases.UpdateCourseByIdUseCase;
import com.imo.backend.contexts.catalog.lesson.events.FirstLessonInCourseUpdatedEvent;
import com.imo.backend.contexts.catalog.lesson.events.LessonsListUpdatedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class CourseEventListener {

  private final UpdateCourseByIdUseCase updateCourseByIdUseCase;

  public CourseEventListener(UpdateCourseByIdUseCase updateCourseByIdUseCase) {
    this.updateCourseByIdUseCase = updateCourseByIdUseCase;
  }

  @Async
  @EventListener
  public void handle(LessonsListUpdatedEvent event) {
    this.updateCourseByIdUseCase.execute(
        event.courseId(),
        new UpdateCourseByIdCommand(
            event.courseId(), null, null, null, null, event.newCount(), null, null));
  }

  @Async
  @EventListener
  public void handle(FirstLessonInCourseUpdatedEvent event) {
    this.updateCourseByIdUseCase.execute(
        event.courseId(),
        new UpdateCourseByIdCommand(
            event.courseId(), null, null, null, null, null, event.newFirstYoutubeLink(), null));
  }
}
