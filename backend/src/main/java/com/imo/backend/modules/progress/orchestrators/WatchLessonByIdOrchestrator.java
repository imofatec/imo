package com.imo.backend.modules.progress.orchestrators;

import com.imo.backend.modules.progress.controllers.dtos.ProgressDTO;

public interface WatchLessonByIdOrchestrator {
  ProgressDTO execute(String lessonToWatchId, String userId);
}
