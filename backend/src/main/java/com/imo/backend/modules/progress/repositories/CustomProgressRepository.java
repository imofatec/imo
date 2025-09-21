package com.imo.backend.modules.progress.repositories;

import com.imo.backend.modules.progress.Progress;
import com.imo.backend.modules.progress.ProgressDetails;

import java.util.List;
import java.util.Optional;

public interface CustomProgressRepository {
  List<Progress> findProgressByCourseId(String courseId, int page, int size);

  Optional<Progress> findByUserIdAndCourseId(String userId, String courseId);

  Optional<ProgressDetails> findDetailsByUserIdAndCourseId(String userId, String courseId);

  List<ProgressDetails> findAllProgressDetailsByUserId(String userId);

  List<ProgressDetails> findAllProgressDetailsByUserId(String userId, int page, int pageSize);
}
