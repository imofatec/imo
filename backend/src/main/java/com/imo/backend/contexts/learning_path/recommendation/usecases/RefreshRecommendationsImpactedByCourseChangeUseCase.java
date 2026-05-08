package com.imo.backend.contexts.learning_path.recommendation.usecases;

import com.imo.backend.contexts.learning_path.recommendation.repositories.RecommendationRepository;
import com.imo.backend.contexts.learning_path.skill_profile.repositories.SkillProfileRepository;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RefreshRecommendationsImpactedByCourseChangeUseCase {
  private final SkillProfileRepository skillProfileRepository;
  private final RecommendationRepository recommendationRepository;
  private final RefreshRecommendationsForUserUseCase refreshRecommendationsForUserUseCase;

  public RefreshRecommendationsImpactedByCourseChangeUseCase(
      SkillProfileRepository skillProfileRepository,
      RecommendationRepository recommendationRepository,
      RefreshRecommendationsForUserUseCase refreshRecommendationsForUserUseCase) {
    this.skillProfileRepository = skillProfileRepository;
    this.recommendationRepository = recommendationRepository;
    this.refreshRecommendationsForUserUseCase = refreshRecommendationsForUserUseCase;
  }

  public void execute(String courseId, List<String> skillIds) {
    Set<String> affectedUserIds = new LinkedHashSet<>();
    affectedUserIds.addAll(this.recommendationRepository.findDistinctUserIdsByCourseId(courseId));
    affectedUserIds.addAll(this.skillProfileRepository.findDistinctUserIdsBySkillIds(skillIds));

    affectedUserIds.forEach(this::refreshRecommendationsSafely);
  }

  private void refreshRecommendationsSafely(String userId) {
    try {
      this.refreshRecommendationsForUserUseCase.execute(userId);
    } catch (Exception exception) {
      log.error("Erro ao recalcular recommendation do usuário {}", userId, exception);
    }
  }
}
