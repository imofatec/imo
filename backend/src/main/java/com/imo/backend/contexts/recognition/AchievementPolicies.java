package com.imo.backend.contexts.recognition;

import com.imo.backend.contexts.recognition.repositories.UserAchievementsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AchievementPolicies {
  private final AchievementMetricsResolver achievementMetricsResolver;

  private final UserAchievementsRepository userAchievementsRepository;

  public AchievementPolicies(
      AchievementMetricsResolver achievementMetricsResolver,
      UserAchievementsRepository userAchievementsRepository) {
    this.achievementMetricsResolver = achievementMetricsResolver;
    this.userAchievementsRepository = userAchievementsRepository;
  }

  public boolean canUnlockAchievement(String userId, Achievement achievement) {
    if (this.isAlreadyGranted(userId, achievement.getKey())) {
      log.debug("achievement ja desbloqueado antes {}", achievement.getKey());
      return false;
    }

    long currentValue = this.achievementMetricsResolver.resolve(userId, achievement.getTrigger());

    log.debug("métrica {} achievement {}", achievement.getKey(), currentValue);

    return this.hasReachedTarget(currentValue, achievement.getTarget());
  }

  private boolean isAlreadyGranted(String userId, String achievementKey) {
    return this.userAchievementsRepository
        .findByAchievementKeyAndUserId(achievementKey, userId)
        .isPresent();
  }

  private boolean hasReachedTarget(long currentValue, int target) {
    return currentValue >= target;
  }
}
