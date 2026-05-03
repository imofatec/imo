package com.imo.backend.contexts.recognition.http.dtos;

import com.imo.backend.contexts.recognition.AchievementTrigger;
import java.time.LocalDateTime;

public record AchievementCardDTO(
    AchievementTrigger trigger,
    String key,
    String title,
    String description,
    String imageUrl,
    LocalDateTime unlockedAt,
    long currentValue,
    int targetValue,
    int progressPercentage) {}
