package com.imo.backend.contexts.journey_tracking.guards;

import com.imo.backend.contexts.journey_tracking.Progress;

import java.util.List;

public interface GetProgressByCourseIdGuard {
  List<Progress> execute(String courseId, int page, int size);
}
