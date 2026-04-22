package com.imo.backend.contexts.identity.achievement;

public record AchievementDefinition(
    AchievementCode code,
    String title,
    String description,
    AchievementTrigger trigger,
    int target) {}
