package com.imo.backend.contexts.recommendation.events;

import com.imo.backend.contexts.recommendation.usecases.RefreshRecommendationsForUserUseCase;
import com.imo.backend.contexts.skill_profile.events.SkillProfileUpdatedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class RecommendationEventListener {
  private final RefreshRecommendationsForUserUseCase refreshRecommendationsForUserUseCase;

  public RecommendationEventListener(
      RefreshRecommendationsForUserUseCase refreshRecommendationsForUserUseCase) {
    this.refreshRecommendationsForUserUseCase = refreshRecommendationsForUserUseCase;
  }

  @Async
  @EventListener
  public void handle(SkillProfileUpdatedEvent event) {
    this.refreshRecommendationsForUserUseCase.execute(event.userId());
  }
}
