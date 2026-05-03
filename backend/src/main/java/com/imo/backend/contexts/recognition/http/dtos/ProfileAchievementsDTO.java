package com.imo.backend.contexts.recognition.http.dtos;

import java.util.List;

public record ProfileAchievementsDTO(
    int total, int unlockedCount, int lockedCount, List<AchievementCardDTO> items) {}
