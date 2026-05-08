package com.imo.backend.contexts.recognition.events;

import com.imo.backend.contexts.recognition.Achievement;

public record AchievementUnlockedEvent(String userId, Achievement achievement) {}
