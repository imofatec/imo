package com.imo.backend.contexts.social.profile.repositories;

import com.imo.backend.contexts.catalog.course.Course;
import com.imo.backend.contexts.catalog.lesson.Lesson;
import com.imo.backend.contexts.common.exceptions.custom.NotFoundException;
import com.imo.backend.contexts.identity.user.User;
import com.imo.backend.contexts.journey_tracking.progress.Progress;
import com.imo.backend.contexts.recognition.UserAchievement;
import com.imo.backend.contexts.recognition.repositories.AchievementRepository;
import com.imo.backend.contexts.social.comment.Comment;
import com.imo.backend.contexts.social.profile.HighlightedAchievementDetails;
import com.imo.backend.contexts.social.profile.LastCommentActivityDetails;
import com.imo.backend.contexts.social.profile.PublicUserDetails;
import com.imo.backend.contexts.social.profile.RecentCourseActivityDetails;
import com.imo.backend.contexts.social.profile.SharedProgressMilestoneDetails;
import com.imo.backend.contexts.social.profile.gateways.PublicUserProfileGateway;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class PublicUserProfileRepository implements PublicUserProfileGateway {
  private static final int HIGHLIGHTED_ACHIEVEMENTS_LIMIT = 10;
  private static final int SHARED_PROGRESS_MILESTONES_LIMIT = 10;
  private static final int RECENT_COURSES_LIMIT = 10;

  private final MongoTemplate mongoTemplate;
  private final AchievementRepository achievementRepository;

  public PublicUserProfileRepository(
      MongoTemplate mongoTemplate, AchievementRepository achievementRepository) {
    this.mongoTemplate = mongoTemplate;
    this.achievementRepository = achievementRepository;
  }

  @Override
  public PublicUserDetails findUserByIdOrThrow(String userId) {
    User user = this.findUserEntityByIdOrThrow(userId);

    return new PublicUserDetails(
        user.getId(),
        user.getName(),
        user.getBio(),
        user.getProfilePicturePath(),
        user.getCategoriesOfInterest());
  }

  private User findUserEntityByIdOrThrow(String userId) {
    User user = this.mongoTemplate.findById(new ObjectId(userId), User.class);

    if (user == null) {
      throw new NotFoundException("Usuário não encontrado");
    }

    return user;
  }

  @Override
  public List<HighlightedAchievementDetails> findLatestUnlockedAchievementsByUserId(
      String userId, int limit) {
    Query query =
        new Query()
            .addCriteria(Criteria.where("userId").is(new ObjectId(userId)))
            .with(Sort.by(Sort.Direction.DESC, "createdAt"))
            .limit(Math.min(limit, HIGHLIGHTED_ACHIEVEMENTS_LIMIT));

    return this.mongoTemplate.find(query, UserAchievement.class).stream()
        .map(
            achievement ->
                this.achievementRepository
                    .findByKey(achievement.getAchievementKey())
                    .map(
                        metadata ->
                            new HighlightedAchievementDetails(
                                metadata.getKey(),
                                metadata.getTitle(),
                                metadata.getDescription(),
                                metadata.getUnlockedImagePath(),
                                achievement.getCreatedAt()))
                    .orElse(null))
        .filter(Objects::nonNull)
        .toList();
  }

  @Override
  public List<SharedProgressMilestoneDetails> findLatestSharedProgressMilestonesByUserId(
      String userId, int limit) {
    Query query =
        new Query()
            .addCriteria(Criteria.where("userId").is(new ObjectId(userId)))
            .with(Sort.by(Sort.Order.desc("updatedAt"), Sort.Order.desc("createdAt")))
            .limit(Math.min(limit, SHARED_PROGRESS_MILESTONES_LIMIT));

    query
        .fields()
        .include("publicCode")
        .include("courseNameSnapshot")
        .include("watchedLessonsCountSnapshot")
        .include("totalLessonsCountSnapshot")
        .include("completionPercentageSnapshot")
        .include("updatedAt")
        .include("createdAt");

    return this.mongoTemplate.find(
        query,
        SharedProgressMilestoneDetails.class,
        this.mongoTemplate.getCollectionName(
            com.imo.backend.contexts.journey_tracking.progress_milestone.ProgressMilestone.class));
  }

  @Override
  public List<RecentCourseActivityDetails> findRecentCourseActivitiesByUserId(
      String userId, int limit) {
    Query query =
        new Query()
            .addCriteria(Criteria.where("userId").is(new ObjectId(userId)))
            .with(Sort.by(Sort.Direction.DESC, "updatedAt"))
            .limit(Math.min(limit, RECENT_COURSES_LIMIT));

    return this.mongoTemplate.find(query, Progress.class).stream()
        .map(this::toRecentCourseActivityDetails)
        .filter(Objects::nonNull)
        .toList();
  }

  @Override
  public Optional<LastCommentActivityDetails> findLastCommentByUserId(String userId) {
    Query query =
        new Query()
            .addCriteria(Criteria.where("userId").is(new ObjectId(userId)))
            .with(Sort.by(Sort.Direction.DESC, "createdAt"))
            .limit(1);

    Comment comment = this.mongoTemplate.findOne(query, Comment.class);

    if (comment == null) {
      return Optional.empty();
    }

    Lesson lesson = this.mongoTemplate.findById(new ObjectId(comment.getLessonId()), Lesson.class);

    if (lesson == null) {
      throw new NotFoundException("Aula não encontrada");
    }

    Course course = this.mongoTemplate.findById(new ObjectId(lesson.getCourseId()), Course.class);

    if (course == null) {
      throw new NotFoundException("Curso não encontrado");
    }

    return Optional.of(
        new LastCommentActivityDetails(
            comment.getId(),
            comment.getContent(),
            comment.getCreatedAt(),
            comment.getLessonId(),
            lesson.getYoutubeLink(),
            lesson.getTitle(),
            lesson.getCourseId(),
            course.getName().name()));
  }

  private RecentCourseActivityDetails toRecentCourseActivityDetails(Progress progress) {
    Course course = this.mongoTemplate.findById(new ObjectId(progress.getCourseId()), Course.class);

    if (course == null) {
      return null;
    }

    int totalLessonsCount = course.getLessonsCount();

    return new RecentCourseActivityDetails(
        progress.getCourseId(),
        course.getName().name(),
        progress.getUpdatedAt(),
        course.getFirstLessonYoutubeLink(),
        progress.getWatchedLessonsCount(),
        totalLessonsCount,
        progress.calculateCompletionPercentage(totalLessonsCount));
  }
}
