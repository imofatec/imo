package com.imo.backend.contexts.journey_tracking.progress_milestone;

import com.imo.backend.contexts.common.Entity;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Document("progress_milestones")
@CompoundIndex(
    name = "progress_milestone_user_course_idx",
    def = "{'userId': 1, 'courseId': 1}",
    unique = true)
@Data
public class ProgressMilestone extends Entity {
  private ObjectId userId;

  private ObjectId courseId;

  @Indexed(unique = true)
  private String publicCode;

  private String authorNameSnapshot;

  private String courseNameSnapshot;

  private int watchedLessonsCountSnapshot;

  private int totalLessonsCountSnapshot;

  private int completionPercentageSnapshot;

  private LocalDateTime courseStartedAtSnapshot;

  private LocalDateTime courseFinishedAtSnapshot;

  private LocalDateTime sourceCourseUpdatedAt;

  public ProgressMilestone() {}

  public ProgressMilestone(String userId, String courseId, String publicCode) {
    this.setUserId(userId);
    this.setCourseId(courseId);
    this.setPublicCode(publicCode);
  }

  public void setUserId(String userId) {
    this.userId = new ObjectId(userId);
  }

  public String getUserId() {
    return this.userId.toString();
  }

  public void setCourseId(String courseId) {
    this.courseId = new ObjectId(courseId);
  }

  public String getCourseId() {
    return this.courseId.toString();
  }

  public void refreshSnapshot(
      String authorNameSnapshot,
      String courseNameSnapshot,
      int watchedLessonsCountSnapshot,
      int totalLessonsCountSnapshot,
      int completionPercentageSnapshot,
      LocalDateTime courseStartedAtSnapshot,
      LocalDateTime courseFinishedAtSnapshot,
      LocalDateTime sourceCourseUpdatedAt) {
    this.authorNameSnapshot = authorNameSnapshot;
    this.courseNameSnapshot = courseNameSnapshot;
    this.watchedLessonsCountSnapshot = watchedLessonsCountSnapshot;
    this.totalLessonsCountSnapshot = totalLessonsCountSnapshot;
    this.completionPercentageSnapshot = completionPercentageSnapshot;
    this.courseStartedAtSnapshot = courseStartedAtSnapshot;
    this.courseFinishedAtSnapshot = courseFinishedAtSnapshot;
    this.sourceCourseUpdatedAt = sourceCourseUpdatedAt;
  }

  public boolean shouldRefreshSnapshot(LocalDateTime currentCourseUpdatedAt) {
    if (this.sourceCourseUpdatedAt == null || currentCourseUpdatedAt == null) {
      return true;
    }

    return currentCourseUpdatedAt.isAfter(this.sourceCourseUpdatedAt);
  }

  public LocalDateTime getGeneratedAt() {
    return this.getUpdatedAt() != null ? this.getUpdatedAt() : this.getCreatedAt();
  }
}
