package com.imo.backend.contexts.recommendation.repositories;

import com.imo.backend.contexts.recommendation.Recommendation;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecommendationRepository
    extends MongoRepository<Recommendation, String>, CustomRecommendationRepository {
  Optional<Recommendation> findByUserId(ObjectId userId);

  default Optional<Recommendation> findByUserId(String userId) {
    return this.findByUserId(new ObjectId(userId));
  }
}
