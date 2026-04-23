package com.imo.backend.contexts.recognition.gateways;

public interface AchievementMetricsGateway {
  long countFinishedCoursesByUserId(String userId);

  long countWatchedLessonsByUserId(String userId);
}
