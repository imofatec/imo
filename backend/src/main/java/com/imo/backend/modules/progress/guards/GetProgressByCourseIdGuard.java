package com.imo.backend.modules.progress.guards;

import com.imo.backend.modules.progress.Progress;

import java.util.List;

public interface GetProgressByCourseIdGuard {
  List<Progress> execute(String courseId, int page, int size);
}
