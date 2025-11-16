package com.imo.backend.modules.progress.guards;

import com.imo.backend.modules.progress.Progress;

public interface GetProgressByUserIdAndCourseIdGuard {
  Progress execute(String userId, String courseId);
}
