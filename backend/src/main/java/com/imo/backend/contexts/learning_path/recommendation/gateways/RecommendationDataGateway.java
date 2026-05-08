package com.imo.backend.contexts.learning_path.recommendation.gateways;

import com.imo.backend.contexts.catalog.course.Course;
import com.imo.backend.contexts.learning_path.recommendation.RecommendationCourseDetails;
import com.imo.backend.contexts.learning_path.recommendation.RecommendationSkillDetails;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RecommendationDataGateway {
  void assertUserExists(String userId);

  Set<String> findFinishedCourseIdsByUserId(String userId);

  List<Course> findActiveCoursesByIds(List<String> courseIds);

  List<String> findLatestActiveCourseIdsExcluding(List<String> excludedCourseIds, int limit);

  List<RecommendationCourseDetails> findRecommendationCandidateCourseDetails(
      Set<String> knownSkillIds, Set<String> excludedCourseIds);

  Map<String, RecommendationSkillDetails> findRecommendationSkillDetailsByIds(Set<String> skillIds);
}
