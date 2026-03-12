package com.imo.backend.contexts.journey_tracking.orchestrators;

import com.imo.backend.contexts.journey_tracking.controllers.dtos.ProgressDTO;

public interface WatchLessonByIdOrchestrator {
  ProgressDTO execute(String lessonToWatchId, String userId);
}
