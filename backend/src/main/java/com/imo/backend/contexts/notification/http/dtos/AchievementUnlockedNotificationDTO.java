package com.imo.backend.contexts.notification.http.dtos;

import com.imo.backend.contexts.recognition.Achievement;

public record AchievementUnlockedNotificationDTO(
    String key, String title, String description, String imageUrl) {
  public static AchievementUnlockedNotificationDTO fromAchievement(Achievement achievement) {
    return new AchievementUnlockedNotificationDTO(
        achievement.getKey(),
        achievement.getTitle(),
        achievement.getDescription(),
        achievement.getUnlockedImagePath());
  }
}
