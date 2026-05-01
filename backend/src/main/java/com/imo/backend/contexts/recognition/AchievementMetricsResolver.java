package com.imo.backend.contexts.recognition;

import com.imo.backend.contexts.recognition.gateways.AchievementMetricsGateway;
import java.util.EnumMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class AchievementMetricsResolver {
  private final AchievementMetricsGateway achievementMetricsGateway;

  public AchievementMetricsResolver(AchievementMetricsGateway achievementMetricsGateway) {
    this.achievementMetricsGateway = achievementMetricsGateway;
  }

  public long resolve(String userId, AchievementTrigger trigger) {
    return switch (trigger) {
      case COURSE_FINISHED -> this.achievementMetricsGateway.countFinishedCoursesByUserId(userId);
      case LESSON_WATCHED -> this.achievementMetricsGateway.countWatchedLessonsByUserId(userId);
    };
  }

  public Map<AchievementTrigger, Long> resolveAll(String userId) {
    Map<AchievementTrigger, Long> metricsByTrigger = new EnumMap<>(AchievementTrigger.class);

    for (AchievementTrigger trigger : AchievementTrigger.values()) {
      metricsByTrigger.put(trigger, this.resolve(userId, trigger));
    }

    return metricsByTrigger;
  }
}
