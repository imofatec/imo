package com.imo.backend.contexts.journey_tracking.progress_milestone.usecases;

import com.imo.backend.contexts.catalog.course.Course;
import com.imo.backend.contexts.journey_tracking.progress.Progress;
import com.imo.backend.contexts.journey_tracking.progress.ProgressDetails;
import com.imo.backend.contexts.journey_tracking.progress_milestone.ProgressMilestone;
import com.imo.backend.contexts.journey_tracking.progress_milestone.ProgressMilestonePolicies;
import com.imo.backend.contexts.journey_tracking.progress_milestone.repositories.ProgressMilestoneRepository;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class CreateProgressMilestoneUseCase {
  private final ProgressMilestonePolicies progressMilestonePolicies;
  private final ProgressMilestoneRepository progressMilestoneRepository;

  public CreateProgressMilestoneUseCase(
      ProgressMilestonePolicies progressMilestonePolicies,
      ProgressMilestoneRepository progressMilestoneRepository) {
    this.progressMilestonePolicies = progressMilestonePolicies;
    this.progressMilestoneRepository = progressMilestoneRepository;
  }

  public ProgressMilestone execute(String userId, String courseId) {
    ProgressDetails progressDetails =
        this.progressMilestonePolicies.assertCourseIsFinished(userId, courseId);

    Course course = progressDetails.course();

    ProgressMilestone existingMilestone =
        this.progressMilestoneRepository.findByUserIdAndCourseId(userId, courseId).orElse(null);

    LocalDateTime courseUpdatedAt =
        course.getUpdatedAt() != null ? course.getUpdatedAt() : course.getCreatedAt();

    if (existingMilestone == null) {
      ProgressMilestone milestone =
          new ProgressMilestone(userId, courseId, UUID.randomUUID().toString().replace("-", ""));
      this.applySnapshot(milestone, progressDetails, courseUpdatedAt);
      return this.progressMilestoneRepository.save(milestone);
    }

    if (!existingMilestone.shouldRefreshSnapshot(courseUpdatedAt)) {
      return existingMilestone;
    }

    this.applySnapshot(existingMilestone, progressDetails, courseUpdatedAt);
    return this.progressMilestoneRepository.save(existingMilestone);
  }

  private void applySnapshot(
      ProgressMilestone milestone, ProgressDetails progressDetails, LocalDateTime courseUpdatedAt) {
    Progress progress = progressDetails.progress();
    Course course = progressDetails.course();

    milestone.refreshSnapshot(
        progressDetails.user().getName(),
        course.getName().name(),
        progress.getWatchedLessonsCount(),
        course.getLessonsCount(),
        progress.calculateCompletionPercentage(course.getLessonsCount()),
        progress.getProgressPeriod().startedAt(),
        progress.getProgressPeriod().finishedAt(),
        courseUpdatedAt);
  }
}
