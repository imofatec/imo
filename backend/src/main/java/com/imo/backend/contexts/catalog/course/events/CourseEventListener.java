package com.imo.backend.contexts.catalog.course.events;

import com.imo.backend.contexts.catalog.course.actions.IncLessonsCountByIdAction;
import com.imo.backend.contexts.catalog.course.actions.UpdateCourseByIdAction;
import com.imo.backend.contexts.catalog.course.actions.inputs.UpdateCourseByIdInput;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class CourseEventListener {
  private final UpdateCourseByIdAction updateCourseByIdAction;

  private final IncLessonsCountByIdAction incLessonsCountByIdAction;

  public CourseEventListener(
      UpdateCourseByIdAction updateCourseByIdAction,
      IncLessonsCountByIdAction incLessonsCountByIdAction
  ) {
    this.updateCourseByIdAction = updateCourseByIdAction;
    this.incLessonsCountByIdAction = incLessonsCountByIdAction;
  }

  @Async
  @EventListener
  public void handle(UpdateCourseLessonsCountEvent event) {
    this.updateCourseByIdAction.execute(
        event.courseId(),
        new UpdateCourseByIdInput(null, null, null, null, event.newCount(), null)
    );
  }

  @Async
  @EventListener
  public void handle(IncLessonsCountEvent event) {
    this.incLessonsCountByIdAction.execute(event.courseId());
  }

  @Async
  @EventListener
  public void handle(UpdateCourseFirstYoutubeLinkEvent event) {
    this.updateCourseByIdAction.execute(
        event.courseId(),
        new UpdateCourseByIdInput(null, null, null, null, null, event.newFirstYoutubeLink())
    );
  }
}
