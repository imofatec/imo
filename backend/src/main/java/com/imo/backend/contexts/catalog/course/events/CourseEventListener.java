package com.imo.backend.contexts.catalog.course.events;

import com.imo.backend.contexts.catalog.course.commands.UpdateCourseByIdCommand;
import com.imo.backend.contexts.catalog.course.usecases.UpdateCourseByIdUseCase;
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
  public void handle(UpdateCourseLessonsCountEvent event) {
    this.updateCourseByIdUseCase.execute(
        event.courseId(),
        new UpdateCourseByIdCommand(
            event.courseId(), null, null, null, null, event.newCount(), null));
  }

  @Async
  @EventListener
  public void handle(UpdateCourseFirstYoutubeLinkEvent event) {
    this.updateCourseByIdUseCase.execute(
        event.courseId(),
        new UpdateCourseByIdCommand(
            event.courseId(), null, null, null, null, null, event.newFirstYoutubeLink()));
  }
}
