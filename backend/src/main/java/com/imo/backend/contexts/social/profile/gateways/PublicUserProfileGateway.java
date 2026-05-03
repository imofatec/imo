package com.imo.backend.contexts.social.profile.gateways;

import com.imo.backend.contexts.social.profile.HighlightedAchievementDetails;
import com.imo.backend.contexts.social.profile.LastCommentActivityDetails;
import com.imo.backend.contexts.social.profile.PublicUserDetails;
import com.imo.backend.contexts.social.profile.RecentCourseActivityDetails;
import java.util.List;
import java.util.Optional;

public interface PublicUserProfileGateway {
  PublicUserDetails findUserByIdOrThrow(String userId);

  List<HighlightedAchievementDetails> findLatestUnlockedAchievementsByUserId(
      String userId, int limit);

  List<RecentCourseActivityDetails> findRecentCourseActivitiesByUserId(String userId, int limit);

  Optional<LastCommentActivityDetails> findLastCommentByUserId(String userId);
}
