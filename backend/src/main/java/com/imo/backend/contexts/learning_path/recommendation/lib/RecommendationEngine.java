package com.imo.backend.contexts.learning_path.recommendation.lib;

import java.util.List;

public interface RecommendationEngine {
  List<String> rankRecommendedCourseIds(RecommendationInput input);
}
