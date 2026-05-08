package com.imo.backend.contexts.social.profile.usecases;

import com.imo.backend.contexts.social.profile.gateways.PublicUserProfileGateway;
import com.imo.backend.contexts.social.profile.http.dtos.*;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GetPublicUserProfileUseCase {
  private static final int HIGHLIGHTED_ACHIEVEMENTS_LIMIT = 3;
  private static final int SHARED_PROGRESS_MILESTONES_LIMIT = 1;
  private static final int RECENT_COURSES_LIMIT = 3;

  private final PublicUserProfileGateway publicUserProfileGateway;

  public GetPublicUserProfileUseCase(PublicUserProfileGateway publicUserProfileGateway) {
    this.publicUserProfileGateway = publicUserProfileGateway;
  }

  public PublicUserProfileDTO execute(String userId, String baseUrl) {
    var user = this.publicUserProfileGateway.findUserByIdOrThrow(userId);

    List<HighlightedAchievementDTO> highlightedAchievements =
        this.publicUserProfileGateway
            .findLatestUnlockedAchievementsByUserId(userId, HIGHLIGHTED_ACHIEVEMENTS_LIMIT)
            .stream()
            .map(HighlightedAchievementDTO::fromDetails)
            .toList();

    List<RecentCourseActivityDTO> recentCourses =
        this.publicUserProfileGateway
            .findRecentCourseActivitiesByUserId(userId, RECENT_COURSES_LIMIT)
            .stream()
            .map(RecentCourseActivityDTO::fromDetails)
            .toList();

    LastCommentActivityDTO lastComment =
        this.publicUserProfileGateway
            .findLastCommentByUserId(userId)
            .map(LastCommentActivityDTO::fromDetails)
            .orElse(null);

    List<SharedProgressMilestoneDTO> sharedProgressMilestones =
        this.publicUserProfileGateway
            .findLatestSharedProgressMilestonesByUserId(userId, SHARED_PROGRESS_MILESTONES_LIMIT)
            .stream()
            .map(milestone -> SharedProgressMilestoneDTO.fromDetails(milestone, baseUrl))
            .toList();

    return new PublicUserProfileDTO(
        user.name(),
        user.bio(),
        user.profilePicturePath(),
        user.categoriesOfInterest(),
        highlightedAchievements,
        sharedProgressMilestones,
        new LastActivityDTO(recentCourses, lastComment));
  }
}
