package com.imo.backend.contexts.social.profile.usecases;

import com.imo.backend.contexts.social.profile.gateways.PublicUserProfileGateway;
import com.imo.backend.contexts.social.profile.http.dtos.*;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GetPublicUserProfileUseCase {
  private static final int HIGHLIGHTED_ACHIEVEMENTS_LIMIT = 3;
  private static final int RECENT_COURSES_LIMIT = 3;

  private final PublicUserProfileGateway publicUserProfileGateway;

  public GetPublicUserProfileUseCase(PublicUserProfileGateway publicUserProfileGateway) {
    this.publicUserProfileGateway = publicUserProfileGateway;
  }

  public PublicUserProfileDTO execute(String userId) {
    var user = this.publicUserProfileGateway.findUserByIdOrThrow(userId);

    List<HighlightedAchievementDTO> highlightedAchievements =
        this.publicUserProfileGateway
            .findLatestUnlockedAchievementsByUserId(userId, HIGHLIGHTED_ACHIEVEMENTS_LIMIT)
            .stream()
            .map(
                highlightedAchievement ->
                    new HighlightedAchievementDTO(
                        highlightedAchievement.key(),
                        highlightedAchievement.title(),
                        highlightedAchievement.description(),
                        highlightedAchievement.imageUrl(),
                        highlightedAchievement.unlockedAt()))
            .toList();

    List<RecentCourseActivityDTO> recentCourses =
        this.publicUserProfileGateway
            .findRecentCourseActivitiesByUserId(userId, RECENT_COURSES_LIMIT)
            .stream()
            .map(
                recentCourse ->
                    new RecentCourseActivityDTO(
                        recentCourse.courseId(),
                        recentCourse.courseName(),
                        recentCourse.firstLessonYoutubeLink(),
                        recentCourse.watchedLessonsCount(),
                        recentCourse.totalLessonsCount()))
            .toList();

    LastCommentActivityDTO lastComment =
        this.publicUserProfileGateway
            .findLastCommentByUserId(userId)
            .map(
                comment ->
                    new LastCommentActivityDTO(
                        comment.content(),
                        comment.createdAt(),
                        comment.lessonTitle(),
                        comment.lessonYoutubeLink(),
                        comment.courseId(),
                        comment.courseName()))
            .orElse(null);

    return new PublicUserProfileDTO(
        user.name(),
        user.bio(),
        user.profilePicturePath(),
        user.categoriesOfInterest(),
        highlightedAchievements,
        new LastActivityDTO(recentCourses, lastComment));
  }
}
