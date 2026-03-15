package com.imo.backend.contexts.journey_tracking.services;

import com.imo.backend.contexts.journey_tracking.Progress;

public interface CreateProgressService {
  Progress execute(String userId, String courseId, String firstLessonId);
}
