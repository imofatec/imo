package com.imo.backend.contexts.learning_path.recommendation.events;

import com.imo.backend.contexts.catalog.course.events.CourseCreatedEvent;
import com.imo.backend.contexts.catalog.course.events.CourseSkillIdsChangedEvent;
import com.imo.backend.contexts.catalog.course.events.CourseStatusToggledEvent;
import com.imo.backend.contexts.learning_path.recommendation.usecases.RefreshRecommendationsImpactedByCourseChangeUseCase;
import java.util.LinkedHashSet;
import java.util.List;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class CatalogCourseRecommendationEventListener {
  private final RefreshRecommendationsImpactedByCourseChangeUseCase
      refreshRecommendationsImpactedByCourseChangeUseCase;

  public CatalogCourseRecommendationEventListener(
      RefreshRecommendationsImpactedByCourseChangeUseCase
          refreshRecommendationsImpactedByCourseChangeUseCase) {
    this.refreshRecommendationsImpactedByCourseChangeUseCase =
        refreshRecommendationsImpactedByCourseChangeUseCase;
  }

  @Async
  @EventListener
  public void handle(CourseCreatedEvent event) {
    this.refreshRecommendationsImpactedByCourseChangeUseCase.execute(
        event.courseId(), event.skillIds());
  }

  @Async
  @EventListener
  public void handle(CourseSkillIdsChangedEvent event) {
    this.refreshRecommendationsImpactedByCourseChangeUseCase.execute(
        event.courseId(), this.mergeSkillIds(event.previousSkillIds(), event.currentSkillIds()));
  }

  @Async
  @EventListener
  public void handle(CourseStatusToggledEvent event) {
    this.refreshRecommendationsImpactedByCourseChangeUseCase.execute(
        event.courseId(), event.skillIds());
  }

  private List<String> mergeSkillIds(List<String> previousSkillIds, List<String> currentSkillIds) {
    LinkedHashSet<String> relatedSkillIds = new LinkedHashSet<>();
    relatedSkillIds.addAll(previousSkillIds);
    relatedSkillIds.addAll(currentSkillIds);

    return List.copyOf(relatedSkillIds);
  }
}
