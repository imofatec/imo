package com.imo.backend.contexts.social.profile.http.dtos;

import com.imo.backend.contexts.catalog.course.Categories;
import java.util.List;

public record PublicUserProfileDTO(
    String name,
    String bio,
    String profilePicturePath,
    List<Categories> categoriesOfInterest,
    List<HighlightedAchievementDTO> highlightedAchievements,
    List<SharedProgressMilestoneDTO> sharedProgressMilestones,
    LastActivityDTO lastActivity) {}
