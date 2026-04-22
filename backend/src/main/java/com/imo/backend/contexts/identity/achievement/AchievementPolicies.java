package com.imo.backend.contexts.identity.achievement;

import com.imo.backend.contexts.identity.achievement.gateways.AchievementMetricsGateway;
import com.imo.backend.contexts.identity.achievement.repositories.UserAchievementsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AchievementPolicies {
  private final AchievementMetricsGateway achievementMetricsGateway;

  private final UserAchievementsRepository userAchievementsRepository;

  public AchievementPolicies(
      AchievementMetricsGateway achievementMetricsGateway,
      UserAchievementsRepository userAchievementsRepository) {
    this.achievementMetricsGateway = achievementMetricsGateway;
    this.userAchievementsRepository = userAchievementsRepository;
  }

  public boolean canUnlockAchievement(String userId, AchievementDefinition achievement) {
    boolean alreadyGranted =
        this.userAchievementsRepository
            .findByAchievementCodeAndUserId(achievement.code(), userId)
            .isPresent();

    if (alreadyGranted) {
      log.debug("achievement ja desbloqueado antes {}", achievement.code());
      return false;
    }

    long currentValue = this.resolveMetric(userId, achievement.trigger());

    log.debug("métrica {} achievement {}", achievement.code(), currentValue);

    return currentValue >= achievement.target();
  }

  private long resolveMetric(String userId, AchievementTrigger trigger) {
    return switch (trigger) {
      case COURSE_FINISHED -> this.achievementMetricsGateway.countFinishedCoursesByUserId(userId);
      case LESSON_WATCHED -> this.achievementMetricsGateway.countWatchedLessonsByUserId(userId);
    };
  }
}
