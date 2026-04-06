package com.imo.backend.contexts.journey_tracking.actions;

import com.imo.backend.contexts.journey_tracking.Progress;
import java.util.List;

public interface CreateProgressAction {
  Progress execute(String userId, String courseId, List<String> lessonWatched);
}
