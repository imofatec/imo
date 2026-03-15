package com.imo.backend.contexts.journey_tracking.usecases;

import com.imo.backend.contexts.journey_tracking.Progress;

public interface CreateProgressUseCase {
  Progress execute(String userId, String courseId, String firstLessonId);
}
