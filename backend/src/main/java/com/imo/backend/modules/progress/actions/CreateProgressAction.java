package com.imo.backend.modules.progress.actions;

import com.imo.backend.modules.progress.Progress;

import java.util.List;

public interface CreateProgressAction {
  Progress execute(String userId, String courseId, List<String> lessonWatched);
}
