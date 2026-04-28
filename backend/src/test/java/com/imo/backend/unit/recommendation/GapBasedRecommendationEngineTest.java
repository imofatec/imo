package com.imo.backend.unit.recommendation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.imo.backend.contexts.catalog.course.Course;
import com.imo.backend.contexts.catalog.course.value_objects.Categories;
import com.imo.backend.contexts.catalog.skill.Skill;
import com.imo.backend.contexts.recommendation.lib.RecommendationInput;
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
      "happy path (rankRecommendedCourseIds): ordenar por maior gap, excluir concluídos e preservar apenas cursos elegíveis")
  void shouldRankRecommendationsByGapAndExcludeFinishedCourses() {
    String contributorId = new ObjectId().toString();
    String htmlSkillId = new ObjectId().toString();
    String gitSkillId = new ObjectId().toString();

    Skill htmlSkill = this.createSkill(htmlSkillId, Categories.DEV_WEB, "HTML", 3);
    Skill gitSkill = this.createSkill(gitSkillId, Categories.DEV_WEB, "Git", 1);

    Course finishedCourse =
        this.createCourse(new ObjectId().toString(), contributorId, List.of(htmlSkillId));
    Course higherGapCourse =
        this.createCourse(new ObjectId().toString(), contributorId, List.of(htmlSkillId));
    Course lowerGapCourse =
        this.createCourse(new ObjectId().toString(), contributorId, List.of(gitSkillId));

    RecommendationInput input =
        new RecommendationInput(
            List.of(finishedCourse, higherGapCourse, lowerGapCourse),
            Map.of(htmlSkillId, htmlSkill, gitSkillId, gitSkill),
            Set.of(finishedCourse.getId()),
            Map.of(htmlSkillId, 40, gitSkillId, 40));

    List<String> rankedCourseIds = this.recommendationEngine.rankRecommendedCourseIds(input);

    assertEquals(List.of(higherGapCourse.getId(), lowerGapCourse.getId()), rankedCourseIds);
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
    String contributorId = new ObjectId().toString();
    String reactSkillId = new ObjectId().toString();
    String dockerSkillId = new ObjectId().toString();
    String unknownSkillId = new ObjectId().toString();

    Skill reactSkill = this.createSkill(reactSkillId, Categories.DEV_WEB, "React", 3);
    Skill dockerSkill = this.createSkill(dockerSkillId, Categories.CLOUD, "Docker", 2);

    Course relatedCourse =
        this.createCourse(
            new ObjectId().toString(), contributorId, List.of(reactSkillId, unknownSkillId));
    Course unrelatedCourse =
        this.createCourse(new ObjectId().toString(), contributorId, List.of(dockerSkillId));

    RecommendationInput input =
        new RecommendationInput(
            List.of(relatedCourse, unrelatedCourse),
            Map.of(reactSkillId, reactSkill, dockerSkillId, dockerSkill),
            Set.of(),
            Map.of(reactSkillId, 20));

    List<String> rankedCourseIds = this.recommendationEngine.rankRecommendedCourseIds(input);

    assertEquals(List.of(relatedCourse.getId()), rankedCourseIds);
  }
}
