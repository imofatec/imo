package com.imo.backend.modules.progress.guards;

import com.imo.backend.modules.progress.Progress;

public interface GetProgressByIdGuard {
  Progress execute(String id);
}
