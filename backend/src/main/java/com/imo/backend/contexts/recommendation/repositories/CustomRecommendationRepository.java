package com.imo.backend.contexts.recommendation.repositories;

import com.imo.backend.contexts.recommendation.Recommendation;
import java.util.List;

public interface CustomRecommendationRepository {
  Recommendation upsertByUserId(String userId, List<String> courseIds);
}
