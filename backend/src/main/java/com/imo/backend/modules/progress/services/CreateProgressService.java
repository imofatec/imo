package com.imo.backend.modules.progress.services;

import com.imo.backend.modules.progress.Progress;

import java.util.List;

public interface CreateProgressService {
  Progress execute(String userId, String courseId, List<String> lessonsWatched);
}
