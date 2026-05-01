package com.imo.backend.contexts.social.profile;

import java.time.LocalDateTime;

public record HighlightedAchievementDetails(
    String key, String title, String description, String imageUrl, LocalDateTime unlockedAt) {}
