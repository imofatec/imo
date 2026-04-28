package com.imo.backend.contexts.recommendation.repositories;

import com.imo.backend.contexts.recommendation.Recommendation;
import java.util.List;

public interface CustomRecommendationRepository {
  List<String> findDistinctUserIdsByCourseId(String courseId);

  Recommendation upsertByUserId(String userId, List<String> courseIds);
}
