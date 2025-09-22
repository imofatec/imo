package com.imo.backend.modules.progress.guards;

import com.imo.backend.modules.progress.ProgressDetails;

public interface GetProgressDetailsByUserIdAndCourseIdGuard {
  ProgressDetails execute(String userId, String courseId);
}
