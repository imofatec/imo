package com.imo.backend.contexts.recommendation.lib;

import java.util.List;

public interface RecommendationEngine {
  List<String> rankRecommendedCourseIds(RecommendationInput input);
}
