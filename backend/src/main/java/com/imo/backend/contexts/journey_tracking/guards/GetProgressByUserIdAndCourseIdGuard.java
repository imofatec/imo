package com.imo.backend.contexts.journey_tracking.guards;

import com.imo.backend.contexts.journey_tracking.Progress;

public interface GetProgressByUserIdAndCourseIdGuard {
  Progress execute(String userId, String courseId);
}
