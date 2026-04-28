package com.imo.backend.unit.recommendation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.imo.backend.contexts.catalog.course.Course;
import com.imo.backend.contexts.catalog.course.repositories.CourseRepository;
import com.imo.backend.contexts.catalog.course.repositories.CourseSearchParams;
import com.imo.backend.contexts.catalog.course.value_objects.Categories;
import com.imo.backend.contexts.catalog.skill.Skill;
import com.imo.backend.contexts.catalog.skill.repositories.SkillRepository;
import com.imo.backend.contexts.common.CombineWith;
import com.imo.backend.contexts.common.MatchType;
import com.imo.backend.contexts.identity.user.User;
import com.imo.backend.contexts.identity.user.repositories.UserRepository;
import com.imo.backend.contexts.journey_tracking.repositories.ProgressRepository;
import com.imo.backend.contexts.recommendation.Recommendation;
import com.imo.backend.contexts.recommendation.lib.RecommendationEngine;
import com.imo.backend.contexts.recommendation.lib.RecommendationInput;
import com.imo.backend.contexts.recommendation.repositories.RecommendationRepository;
import com.imo.backend.contexts.recommendation.usecases.RefreshRecommendationsForUserUseCase;
import com.imo.backend.contexts.skill_profile.SkillProfile;
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
class RefreshRecommendationsForUserUseCaseTest {
  @Mock private UserRepository userRepository;
  @Mock private SkillProfileRepository skillProfileRepository;
  @Mock private ProgressRepository progressRepository;
  @Mock private CourseRepository courseRepository;
  @Mock private SkillRepository skillRepository;
  @Mock private RecommendationEngine recommendationEngine;
  @Mock private RecommendationRepository recommendationRepository;

  @InjectMocks private RefreshRecommendationsForUserUseCase useCase;

  private User createUser() {
    return new User("Teste", "teste@email.com", "Senha123", true);
  }

  private Skill createSkill(String skillId, Categories category, String name, int isEssential) {
    Skill skill = new Skill(category, name, isEssential);
    skill.setId(skillId);
    return skill;
  }

  private Course createCourse(String courseId, String contributorId, List<String> skillIds) {
    Course course =
        new Course(
            true,
            contributorId,
            "Curso " + courseId.substring(0, 6),
            "Iniciante",
            Categories.DEV_WEB,
            "Descrição do curso",
            "https://www.youtube.com/watch?v=abc123XYZ89",
            1,
            skillIds);
    course.setId(courseId);
    return course;
  }

  @Test
  @DisplayName(
      "happy path (execute): carregar contexto do usuário, delegar ranking ao engine e persistir o cache")
  void shouldDelegateRecommendationRankingAndPersistResult() {
    String userId = new ObjectId().toString();
    String contributorId = new ObjectId().toString();
    String htmlSkillId = new ObjectId().toString();
    String finishedCourseId = new ObjectId().toString();
    String recommendedCourseId = new ObjectId().toString();

    SkillProfile htmlProfile = new SkillProfile(userId, htmlSkillId, 40);
    Skill htmlSkill = this.createSkill(htmlSkillId, Categories.DEV_WEB, "HTML", 2);
    Course activeCourse =
        this.createCourse(recommendedCourseId, contributorId, List.of(htmlSkillId));

    when(this.userRepository.findByIdOrThrow(userId)).thenReturn(this.createUser());
    when(this.skillProfileRepository.findAllByUserId(userId)).thenReturn(List.of(htmlProfile));
    when(this.progressRepository.findFinishedCourseIdsByUserId(userId))
        .thenReturn(List.of(finishedCourseId));
    when(this.courseRepository.search(
            any(CourseSearchParams.class), eq(MatchType.PERFECT), eq(CombineWith.AND), eq(true)))
        .thenReturn(List.of(activeCourse));
    when(this.skillRepository.findAllById(any())).thenReturn(List.of(htmlSkill));
    when(this.recommendationEngine.rankRecommendedCourseIds(any(RecommendationInput.class)))
        .thenReturn(List.of(recommendedCourseId));
    when(this.recommendationRepository.upsertByUserId(userId, List.of(recommendedCourseId)))
        .thenReturn(new Recommendation(userId, List.of(recommendedCourseId)));

    Recommendation recommendation = this.useCase.execute(userId);

    assertEquals(List.of(recommendedCourseId), recommendation.getCourseIdsAsString());
    verify(this.recommendationEngine)
        .rankRecommendedCourseIds(
            argThat(
                input ->
                    input.activeCourses().size() == 1
                        && input.activeCourses().getFirst().getId().equals(activeCourse.getId())
                        && input.skillsById().containsKey(htmlSkillId)
                        && input.finishedCourseIds().contains(finishedCourseId)
                        && input.coverageBySkillId().get(htmlSkillId) == 40));
    verify(this.recommendationRepository).upsertByUserId(userId, List.of(recommendedCourseId));
  }

  @Test
  @DisplayName(
      "functional (execute): persistir recommendation vazia quando o engine não encontrar cursos")
  void shouldPersistEmptyRecommendationWhenEngineReturnsEmptyList() {
    String userId = new ObjectId().toString();

    when(this.userRepository.findByIdOrThrow(userId)).thenReturn(this.createUser());
    when(this.skillProfileRepository.findAllByUserId(userId)).thenReturn(List.of());
    when(this.progressRepository.findFinishedCourseIdsByUserId(userId)).thenReturn(List.of());
    when(this.courseRepository.search(
            any(CourseSearchParams.class), eq(MatchType.PERFECT), eq(CombineWith.AND), eq(true)))
        .thenReturn(List.of());
    when(this.recommendationEngine.rankRecommendedCourseIds(any(RecommendationInput.class)))
        .thenReturn(List.of());
    when(this.recommendationRepository.upsertByUserId(userId, List.of()))
        .thenReturn(new Recommendation(userId, List.of()));

    Recommendation recommendation = this.useCase.execute(userId);

    assertEquals(List.of(), recommendation.getCourseIdsAsString());
    verify(this.recommendationRepository).upsertByUserId(userId, List.of());
  }
}
