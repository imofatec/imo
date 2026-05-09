package com.imo.backend.contexts.journey_tracking.progress_milestone.lib;

public final class ProgressMilestoneUrlBuilder {
  private ProgressMilestoneUrlBuilder() {}

  public static String buildShareUrl(String baseUrl, String publicCode) {
    return baseUrl + "/m/" + publicCode;
  }

  public static String buildImageUrl(String baseUrl, String publicCode) {
    return baseUrl + "/api/progress/milestones/public/" + publicCode + "/image.png";
  }
}
