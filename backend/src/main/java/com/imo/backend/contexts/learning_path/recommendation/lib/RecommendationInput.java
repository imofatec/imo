package com.imo.backend.contexts.learning_path.recommendation.lib;

import com.imo.backend.contexts.learning_path.recommendation.RecommendationCourseDetails;
import com.imo.backend.contexts.learning_path.recommendation.RecommendationSkillDetails;
import java.util.List;
import java.util.Map;
import java.util.Set;

public record RecommendationInput(
    List<RecommendationCourseDetails> candidateCourses,
    Map<String, RecommendationSkillDetails> skillsById,
    Set<String> finishedCourseIds,
    Map<String, Integer> coverageBySkillId) {}
