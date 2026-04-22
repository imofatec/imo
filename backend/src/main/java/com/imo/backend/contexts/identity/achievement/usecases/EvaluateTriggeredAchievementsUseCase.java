package com.imo.backend.contexts.identity.achievement.usecases;

import com.imo.backend.contexts.identity.achievement.*;
import com.imo.backend.contexts.identity.achievement.events.AchievementUnlockedEvent;
import com.imo.backend.contexts.identity.achievement.repositories.UserAchievementsRepository;
import java.util.List;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class EvaluateTriggeredAchievementsUseCase {
  private final AchievementPolicies achievementPolicies;

  private final UserAchievementsRepository userAchievementsRepository;

  private final ApplicationEventPublisher applicationEventPublisher;

  public EvaluateTriggeredAchievementsUseCase(
      AchievementPolicies achievementPolicies,
      UserAchievementsRepository userAchievementsRepository,
      ApplicationEventPublisher applicationEventPublisher) {
    this.achievementPolicies = achievementPolicies;
    this.userAchievementsRepository = userAchievementsRepository;
    this.applicationEventPublisher = applicationEventPublisher;
  }

  public void evaluateByCourseFinished(String userId) {
    this.evaluate(userId, AchievementTrigger.COURSE_FINISHED);
  }

  public void evaluateByLessonWatched(String userId) {
    this.evaluate(userId, AchievementTrigger.LESSON_WATCHED);
  }

  private void evaluate(String userId, AchievementTrigger trigger) {
    List<AchievementDefinition> achievements = AchievementCatalog.findByTrigger(trigger);

    for (AchievementDefinition achievement : achievements) {
      if (!this.achievementPolicies.canUnlockAchievement(userId, achievement)) {
        continue;
      }

      this.userAchievementsRepository.save(new UserAchievement(userId, achievement.code()));
      this.applicationEventPublisher.publishEvent(
          new AchievementUnlockedEvent(userId, achievement));
    }
  }
}
