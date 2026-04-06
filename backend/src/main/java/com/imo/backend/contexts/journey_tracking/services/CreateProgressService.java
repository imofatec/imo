package com.imo.backend.contexts.journey_tracking.services;

import com.imo.backend.contexts.journey_tracking.Progress;
import java.util.List;

public interface CreateProgressService {
  Progress execute(String userId, String courseId, List<String> lessonsWatched);
}
