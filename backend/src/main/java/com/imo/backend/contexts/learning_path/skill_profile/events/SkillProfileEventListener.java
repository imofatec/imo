package com.imo.backend.contexts.learning_path.skill_profile.events;

import com.imo.backend.contexts.journey_tracking.progress.events.CourseFinishedEvent;
import com.imo.backend.contexts.learning_path.skill_profile.usecases.UpdateSkillProfileOnCourseFinishedUseCase;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class SkillProfileEventListener {
  private final UpdateSkillProfileOnCourseFinishedUseCase updateSkillProfileOnCourseFinishedUseCase;

  public SkillProfileEventListener(
      UpdateSkillProfileOnCourseFinishedUseCase updateSkillProfileOnCourseFinishedUseCase) {
    this.updateSkillProfileOnCourseFinishedUseCase = updateSkillProfileOnCourseFinishedUseCase;
  }

  @Async
  @EventListener
  public void handle(CourseFinishedEvent event) {
    this.updateSkillProfileOnCourseFinishedUseCase.execute(event.userId(), event.courseId());
  }
}
