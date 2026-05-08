package com.imo.backend.contexts.social.profile.http.dtos;

import com.imo.backend.contexts.common.HttpDateTimeFormatter;
import com.imo.backend.contexts.journey_tracking.progress_milestone.lib.ProgressMilestoneUrlBuilder;
import com.imo.backend.contexts.social.profile.SharedProgressMilestoneDetails;
import java.time.LocalDateTime;

public record SharedProgressMilestoneDTO(
    String publicCode, String courseName, String generatedAt, String shareUrl, String imageUrl) {
  public static SharedProgressMilestoneDTO fromDetails(
      SharedProgressMilestoneDetails milestone, String baseUrl) {
    return new SharedProgressMilestoneDTO(
        milestone.publicCode(),
        milestone.courseNameSnapshot(),
        HttpDateTimeFormatter.toDateTime(resolveGeneratedAt(milestone)),
        ProgressMilestoneUrlBuilder.buildShareUrl(baseUrl, milestone.publicCode()),
        ProgressMilestoneUrlBuilder.buildImageUrl(baseUrl, milestone.publicCode()));
  }

  private static LocalDateTime resolveGeneratedAt(SharedProgressMilestoneDetails milestone) {
    return milestone.updatedAt() != null ? milestone.updatedAt() : milestone.createdAt();
  }
}
