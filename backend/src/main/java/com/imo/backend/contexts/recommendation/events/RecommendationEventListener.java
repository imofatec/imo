package com.imo.backend.contexts.recommendation.events;

import com.imo.backend.contexts.recommendation.usecases.RefreshRecommendationsForUserUseCase;
import com.imo.backend.contexts.recommendation.usecases.RefreshRecommendationsImpactedByCourseChangeUseCase;
import com.imo.backend.contexts.skill_profile.events.SkillProfileUpdatedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class RecommendationEventListener {
  private final RefreshRecommendationsForUserUseCase refreshRecommendationsForUserUseCase;
  private final RefreshRecommendationsImpactedByCourseChangeUseCase
      refreshRecommendationsImpactedByCourseChangeUseCase;

  public RecommendationEventListener(
      RefreshRecommendationsForUserUseCase refreshRecommendationsForUserUseCase,
      RefreshRecommendationsImpactedByCourseChangeUseCase
          refreshRecommendationsImpactedByCourseChangeUseCase) {
    this.refreshRecommendationsForUserUseCase = refreshRecommendationsForUserUseCase;
    this.refreshRecommendationsImpactedByCourseChangeUseCase =
        refreshRecommendationsImpactedByCourseChangeUseCase;
  }

  @Async
  @EventListener
  public void handle(SkillProfileUpdatedEvent event) {
    this.refreshRecommendationsForUserUseCase.execute(event.userId());
  }

  @Async
  @EventListener
  public void handle(RecommendationContextChangedEvent event) {
    this.refreshRecommendationsImpactedByCourseChangeUseCase.execute(
        event.courseId(), event.skillIds());
  }
}
