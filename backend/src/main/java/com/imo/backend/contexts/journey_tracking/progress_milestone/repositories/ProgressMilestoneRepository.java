package com.imo.backend.contexts.journey_tracking.progress_milestone.repositories;

import com.imo.backend.contexts.common.exceptions.custom.NotFoundException;
import com.imo.backend.contexts.journey_tracking.progress_milestone.ProgressMilestone;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgressMilestoneRepository extends MongoRepository<ProgressMilestone, String> {
  Optional<ProgressMilestone> findByUserIdAndCourseId(ObjectId userId, ObjectId courseId);

  Optional<ProgressMilestone> findByPublicCode(String publicCode);

  default Optional<ProgressMilestone> findByUserIdAndCourseId(String userId, String courseId) {
    return this.findByUserIdAndCourseId(new ObjectId(userId), new ObjectId(courseId));
  }

  default ProgressMilestone findByPublicCodeOrThrow(String publicCode) {
    return this.findByPublicCode(publicCode)
        .orElseThrow(() -> new NotFoundException("Marco de progresso não encontrado"));
  }
}
