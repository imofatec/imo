package com.imo.backend.contexts.social.profile.http.dtos;

import com.imo.backend.contexts.common.HttpDateTimeFormatter;
import com.imo.backend.contexts.social.profile.HighlightedAchievementDetails;

public record HighlightedAchievementDTO(
    String key, String title, String description, String imageUrl, String unlockedAt) {
  public static HighlightedAchievementDTO fromDetails(HighlightedAchievementDetails achievement) {
    return new HighlightedAchievementDTO(
        achievement.key(),
        achievement.title(),
        achievement.description(),
        achievement.imageUrl(),
        HttpDateTimeFormatter.toDateTime(achievement.unlockedAt()));
  }
}
