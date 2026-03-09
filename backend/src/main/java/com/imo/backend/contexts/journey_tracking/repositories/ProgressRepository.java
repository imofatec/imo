package com.imo.backend.contexts.journey_tracking.repositories;

import com.imo.backend.contexts.common.exceptions.custom.NotFoundException;
import com.imo.backend.contexts.journey_tracking.Progress;
import com.imo.backend.contexts.journey_tracking.ProgressDetails;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProgressRepository
    extends MongoRepository<Progress, String>, CustomProgressRepository {
  default Progress findByIdOrThrow(String id) {
    return findById(id).orElseThrow(() -> new NotFoundException("Progresso nao encontrado"));
  }

  default Progress findByUserIdAndCourseIdOrThrow(String userId, String courseId) {
    return findByUserIdAndCourseId(userId, courseId)
        .orElseThrow(() -> new NotFoundException("Progresso nao encontrado"));
  }

  default ProgressDetails findDetailsByUserIdAndCourseIdOrThrow(String userId, String courseId) {
    return findDetailsByUserIdAndCourseId(userId, courseId)
        .orElseThrow(() -> new NotFoundException("Progresso nao encontrado"));
  }
}
