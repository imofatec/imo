package com.imo.backend.contexts.journey_tracking.guards;

import com.imo.backend.contexts.journey_tracking.Progress;

public interface GetProgressByIdGuard {
  Progress execute(String id);
}
