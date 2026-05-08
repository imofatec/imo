package com.imo.backend.contexts.journey_tracking.progress.repositories;

import com.imo.backend.contexts.common.exceptions.custom.NotFoundException;
import com.imo.backend.contexts.journey_tracking.progress.Progress;
import com.imo.backend.contexts.journey_tracking.progress.ProgressDetails;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProgressRepository
    extends MongoRepository<Progress, String>, CustomProgressRepository {
  default ProgressDetails findProgressDetailsOrThrow(String userId, String courseId) {
    return this.findDetailsByUserIdAndCourseId(userId, courseId)
        .orElseThrow(() -> new NotFoundException("Progresso não encontrado"));
  }
}
