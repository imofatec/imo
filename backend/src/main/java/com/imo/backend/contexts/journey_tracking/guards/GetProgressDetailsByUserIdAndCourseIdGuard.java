package com.imo.backend.contexts.journey_tracking.guards;

import com.imo.backend.contexts.journey_tracking.ProgressDetails;

public interface GetProgressDetailsByUserIdAndCourseIdGuard {
  ProgressDetails execute(String userId, String courseId);
}
