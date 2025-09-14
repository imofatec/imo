package com.imo.backend.modules.course.events;

import com.imo.backend.modules.course.actions.IncLessonsCountByIdAction;
import com.imo.backend.modules.course.actions.UpdateCourseByIdAction;
import com.imo.backend.modules.course.actions.inputs.UpdateCourseInput;
import org.springframework.modulith.events.ApplicationModuleListener;
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

  @ApplicationModuleListener
  public void handle(UpdateCourseLessonsCountEvent event) {
    this.updateCourseByIdAction.execute(
        event.courseId(),
        new UpdateCourseInput(null, null, null, null, event.newCount())
    );
  }

  @ApplicationModuleListener
  public void handle(IncLessonsCountEvent event) {
    this.incLessonsCountByIdAction.execute(event.courseId());
  }
}
