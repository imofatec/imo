package com.imo.backend.unit.recommendation;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.imo.backend.contexts.recommendation.repositories.RecommendationRepository;
import com.imo.backend.contexts.recommendation.usecases.RefreshRecommendationsForUserUseCase;
import com.imo.backend.contexts.recommendation.usecases.RefreshRecommendationsImpactedByCourseChangeUseCase;
import com.imo.backend.contexts.skill_profile.repositories.SkillProfileRepository;
import java.util.List;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RefreshRecommendationsImpactedByCourseChangeUseCaseTest {
  @Mock private SkillProfileRepository skillProfileRepository;
  @Mock private RecommendationRepository recommendationRepository;
  @Mock private RefreshRecommendationsForUserUseCase refreshRecommendationsForUserUseCase;

  @InjectMocks private RefreshRecommendationsImpactedByCourseChangeUseCase useCase;

  @Test
  @DisplayName("happy path (execute): recalcular somente usuários impactados sem duplicar")
  void shouldRefreshOnlyImpactedUsersWithoutDuplicates() {
    String courseId = new ObjectId().toString();
    String userIdA = new ObjectId().toString();
    String userIdB = new ObjectId().toString();
    String userIdC = new ObjectId().toString();
    List<String> skillIds = List.of(new ObjectId().toString(), new ObjectId().toString());

    when(this.recommendationRepository.findDistinctUserIdsByCourseId(courseId))
        .thenReturn(List.of(userIdA, userIdB));
    when(this.skillProfileRepository.findDistinctUserIdsBySkillIds(skillIds))
        .thenReturn(List.of(userIdB, userIdC));

    this.useCase.execute(courseId, skillIds);

    verify(this.refreshRecommendationsForUserUseCase, times(1)).execute(userIdA);
    verify(this.refreshRecommendationsForUserUseCase, times(1)).execute(userIdB);
    verify(this.refreshRecommendationsForUserUseCase, times(1)).execute(userIdC);
  }
}
