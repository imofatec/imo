package com.imo.backend.contexts.social.profile.http.dtos;

import com.imo.backend.contexts.common.HttpDateTimeFormatter;
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
        buildShareUrl(baseUrl, milestone.publicCode()),
        buildImageUrl(baseUrl, milestone.publicCode()));
  }

  static String buildShareUrl(String baseUrl, String publicCode) {
    return baseUrl + "/m/" + publicCode;
  }

  static String buildImageUrl(String baseUrl, String publicCode) {
    return baseUrl + "/api/progress/milestones/public/" + publicCode + "/image.png";
  }

  private static LocalDateTime resolveGeneratedAt(SharedProgressMilestoneDetails milestone) {
    return milestone.updatedAt() != null ? milestone.updatedAt() : milestone.createdAt();
  }
}
