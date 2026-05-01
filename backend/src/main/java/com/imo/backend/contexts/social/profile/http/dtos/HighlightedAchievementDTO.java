package com.imo.backend.contexts.social.profile.http.dtos;

import java.time.LocalDateTime;

public record HighlightedAchievementDTO(
    String key, String title, String description, String imageUrl, LocalDateTime unlockedAt) {}
