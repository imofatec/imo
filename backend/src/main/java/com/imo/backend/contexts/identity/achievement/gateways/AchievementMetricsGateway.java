package com.imo.backend.contexts.identity.achievement.gateways;

public interface AchievementMetricsGateway {
  long countFinishedCoursesByUserId(String userId);

  long countWatchedLessonsByUserId(String userId);
}
