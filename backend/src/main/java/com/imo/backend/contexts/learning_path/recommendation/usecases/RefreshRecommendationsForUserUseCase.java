package com.imo.backend.contexts.learning_path.recommendation.usecases;

import com.imo.backend.contexts.learning_path.recommendation.Recommendation;
import com.imo.backend.contexts.learning_path.recommendation.RecommendationCourseDetails;
import com.imo.backend.contexts.learning_path.recommendation.RecommendationSkillDetails;
import com.imo.backend.contexts.learning_path.recommendation.gateways.RecommendationDataGateway;
import com.imo.backend.contexts.learning_path.recommendation.lib.RecommendationEngine;
import com.imo.backend.contexts.learning_path.recommendation.lib.RecommendationInput;
import com.imo.backend.contexts.learning_path.recommendation.repositories.RecommendationRepository;
import com.imo.backend.contexts.learning_path.skill_profile.SkillProfile;
import com.imo.backend.contexts.learning_path.skill_profile.repositories.SkillProfileRepository;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class RefreshRecommendationsForUserUseCase {
  private final RecommendationDataGateway recommendationDataGateway;
  private final SkillProfileRepository skillProfileRepository;
  private final RecommendationEngine recommendationEngine;
  private final RecommendationRepository recommendationRepository;

  public RefreshRecommendationsForUserUseCase(
      RecommendationDataGateway recommendationDataGateway,
      SkillProfileRepository skillProfileRepository,
      RecommendationEngine recommendationEngine,
      RecommendationRepository recommendationRepository) {
    this.recommendationDataGateway = recommendationDataGateway;
    this.skillProfileRepository = skillProfileRepository;
    this.recommendationEngine = recommendationEngine;
    this.recommendationRepository = recommendationRepository;
  }

  public Recommendation execute(String userId) {
    this.recommendationDataGateway.assertUserExists(userId);

    Map<String, Integer> coverageBySkillId = this.loadCoverageBySkillId(userId);
    if (coverageBySkillId.isEmpty()) {
      return this.recommendationRepository.upsertByUserId(userId, List.of());
    }

    Set<String> finishedCourseIds =
        this.recommendationDataGateway.findFinishedCourseIdsByUserId(userId);
    List<RecommendationCourseDetails> candidateCourses =
        this.recommendationDataGateway.findRecommendationCandidateCourseDetails(
            coverageBySkillId.keySet(), finishedCourseIds);

    if (candidateCourses.isEmpty()) {
      return this.recommendationRepository.upsertByUserId(userId, List.of());
    }

    Map<String, RecommendationSkillDetails> skillsById = this.loadSkillsById(candidateCourses);
    List<String> recommendedCourseIds =
        this.recommendationEngine.rankRecommendedCourseIds(
            new RecommendationInput(
                candidateCourses, skillsById, finishedCourseIds, coverageBySkillId));

    return this.recommendationRepository.upsertByUserId(userId, recommendedCourseIds);
  }

  private Map<String, Integer> loadCoverageBySkillId(String userId) {
    return this.skillProfileRepository.findAllByUserId(userId).stream()
        .collect(
            Collectors.toMap(
                SkillProfile::getSkillId, SkillProfile::getCoverage, (left, right) -> right));
  }

  private Map<String, RecommendationSkillDetails> loadSkillsById(
      List<RecommendationCourseDetails> courses) {
    Set<String> skillIds =
        courses.stream().flatMap(course -> course.skillIds().stream()).collect(Collectors.toSet());

    return this.recommendationDataGateway.findRecommendationSkillDetailsByIds(skillIds);
  }
}
