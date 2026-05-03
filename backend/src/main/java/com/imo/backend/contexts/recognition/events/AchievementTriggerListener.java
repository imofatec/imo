package com.imo.backend.contexts.recognition.events;

import com.imo.backend.contexts.journey_tracking.events.CourseFinishedEvent;
import com.imo.backend.contexts.journey_tracking.events.LessonWatchedEvent;
import com.imo.backend.contexts.recognition.usecases.EvaluateTriggeredAchievementsUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AchievementTriggerListener {
  private final EvaluateTriggeredAchievementsUseCase evaluateTriggeredAchievementsUseCase;

  public AchievementTriggerListener(
      EvaluateTriggeredAchievementsUseCase evaluateTriggeredAchievementsUseCase) {
    this.evaluateTriggeredAchievementsUseCase = evaluateTriggeredAchievementsUseCase;
  }

  @Async
  @EventListener
  public void handle(CourseFinishedEvent event) {
    this.evaluateTriggeredAchievementsUseCase.evaluateByCourseFinished(event.userId());
    log.debug("COURSE_FINISHED_EVENT: avaliação para desbloquear achievement chamada");
  }

  @Async
  @EventListener
  public void handle(LessonWatchedEvent event) {
    this.evaluateTriggeredAchievementsUseCase.evaluateByLessonWatched(event.userId());
    log.debug("LESSON_WATCHED_EVENT: avaliação para desbloquear achievement chamada");
  }
}
