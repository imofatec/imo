package com.imo.backend.contexts.identity.achievement.events;

import com.imo.backend.contexts.identity.achievement.AchievementDefinition;

public record AchievementUnlockedEvent(String userId, AchievementDefinition achievement) {}
