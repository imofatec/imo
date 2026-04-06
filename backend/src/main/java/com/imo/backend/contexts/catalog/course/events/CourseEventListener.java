package com.imo.backend.contexts.catalog.course.events;

import com.imo.backend.contexts.catalog.course.actions.commands.UpdateCourseByIdCommand;
import com.imo.backend.contexts.catalog.course.usecases.IncLessonsCountByIdUseCase;
import com.imo.backend.contexts.catalog.course.usecases.UpdateCourseByIdUseCase;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Component
public class CourseEventListener {

  private final UpdateCourseByIdUseCase updateCourseByIdUseCase;
  private final IncLessonsCountByIdUseCase incLessonsCountByIdUseCase;

  public CourseEventListener(
      UpdateCourseByIdUseCase updateCourseByIdUseCase,
      IncLessonsCountByIdUseCase incLessonsCountByIdUseCase) {
    this.updateCourseByIdUseCase = updateCourseByIdUseCase;
    this.incLessonsCountByIdUseCase = incLessonsCountByIdUseCase;
  }

  @ApplicationModuleListener
  public void handle(UpdateCourseLessonsCountEvent event) {
    this.updateCourseByIdUseCase.execute(
        new UpdateCourseByIdCommand(
            event.courseId(), null, null, null, null, event.newCount(), null));
  }

  @ApplicationModuleListener
  public void handle(IncLessonsCountEvent event) {
    this.incLessonsCountByIdUseCase.execute(event.courseId());
  }

  @ApplicationModuleListener
  public void handle(UpdateCourseFirstYoutubeLinkEvent event) {
    this.updateCourseByIdUseCase.execute(
        new UpdateCourseByIdCommand(
            event.courseId(), null, null, null, null, null, event.newFirstYoutubeLink()));
  }
}
