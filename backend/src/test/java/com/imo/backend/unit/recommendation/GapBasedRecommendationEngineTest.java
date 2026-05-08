package com.imo.backend.unit.recommendation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.imo.backend.contexts.learning_path.recommendation.RecommendationCourseDetails;
import com.imo.backend.contexts.learning_path.recommendation.RecommendationSkillDetails;
import com.imo.backend.contexts.learning_path.recommendation.lib.RecommendationInput;
import com.imo.backend.lib.recommendation.GapBasedRecommendationEngine;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GapBasedRecommendationEngineTest {
  private final GapBasedRecommendationEngine recommendationEngine =
      new GapBasedRecommendationEngine();

  private RecommendationSkillDetails createSkill(String skillId, int isEssential) {
    return new RecommendationSkillDetails(skillId, isEssential);
  }

  private RecommendationCourseDetails createCourse(String courseId, List<String> skillIds) {
    return new RecommendationCourseDetails(courseId, skillIds);
  }

  @Test
  @DisplayName(
      "happy path (rankRecommendedCourseIds): ordenar por maior gap, excluir concluídos e preservar apenas cursos elegíveis")
  void shouldRankRecommendationsByGapAndExcludeFinishedCourses() {
    String htmlSkillId = new ObjectId().toString();
    String gitSkillId = new ObjectId().toString();

    RecommendationSkillDetails htmlSkill = this.createSkill(htmlSkillId, 3);
    RecommendationSkillDetails gitSkill = this.createSkill(gitSkillId, 1);

    RecommendationCourseDetails finishedCourse =
        this.createCourse(new ObjectId().toString(), List.of(htmlSkillId));
    RecommendationCourseDetails higherGapCourse =
        this.createCourse(new ObjectId().toString(), List.of(htmlSkillId));
    RecommendationCourseDetails lowerGapCourse =
        this.createCourse(new ObjectId().toString(), List.of(gitSkillId));

    RecommendationInput input =
        new RecommendationInput(
            List.of(finishedCourse, higherGapCourse, lowerGapCourse),
            Map.of(htmlSkillId, htmlSkill, gitSkillId, gitSkill),
            Set.of(finishedCourse.id()),
            Map.of(htmlSkillId, 40, gitSkillId, 40));

    List<String> rankedCourseIds = this.recommendationEngine.rankRecommendedCourseIds(input);

    assertEquals(List.of(higherGapCourse.id(), lowerGapCourse.id()), rankedCourseIds);
  }

  @Test
  @DisplayName(
      "functional (rankRecommendedCourseIds): retornar vazio quando o usuário ainda não tem coverage")
  void shouldReturnEmptyWhenUserDoesNotHaveSkillCoverage() {
    RecommendationInput input = new RecommendationInput(List.of(), Map.of(), Set.of(), Map.of());

    List<String> rankedCourseIds = this.recommendationEngine.rankRecommendedCourseIds(input);

    assertTrue(rankedCourseIds.isEmpty());
  }

  @Test
  @DisplayName(
      "functional (rankRecommendedCourseIds): recomendar somente cursos com skills já conhecidas")
  void shouldRecommendOnlyCoursesWithKnownSkills() {
    String reactSkillId = new ObjectId().toString();
    String dockerSkillId = new ObjectId().toString();
    String unknownSkillId = new ObjectId().toString();

    RecommendationSkillDetails reactSkill = this.createSkill(reactSkillId, 3);
    RecommendationSkillDetails dockerSkill = this.createSkill(dockerSkillId, 2);

    RecommendationCourseDetails relatedCourse =
        this.createCourse(new ObjectId().toString(), List.of(reactSkillId, unknownSkillId));
    RecommendationCourseDetails unrelatedCourse =
        this.createCourse(new ObjectId().toString(), List.of(dockerSkillId));

    RecommendationInput input =
        new RecommendationInput(
            List.of(relatedCourse, unrelatedCourse),
            Map.of(reactSkillId, reactSkill, dockerSkillId, dockerSkill),
            Set.of(),
            Map.of(reactSkillId, 20));

    List<String> rankedCourseIds = this.recommendationEngine.rankRecommendedCourseIds(input);

    assertEquals(List.of(relatedCourse.id()), rankedCourseIds);
  }
}
