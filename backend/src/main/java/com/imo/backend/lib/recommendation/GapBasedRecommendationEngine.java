package com.imo.backend.lib.recommendation;

import com.imo.backend.contexts.catalog.course.Course;
import com.imo.backend.contexts.catalog.skill.Skill;
import com.imo.backend.contexts.recommendation.lib.RecommendationEngine;
import com.imo.backend.contexts.recommendation.lib.RecommendationInput;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class GapBasedRecommendationEngine implements RecommendationEngine {
  @Override
  public List<String> rankRecommendedCourseIds(RecommendationInput input) {
    if (input.coverageBySkillId().isEmpty()) {
      return List.of();
    }

    List<Course> candidateCourses =
        input.activeCourses().stream()
            .filter(course -> !input.finishedCourseIds().contains(course.getId()))
            .filter(course -> this.hasRelevantSkill(course, input.coverageBySkillId().keySet()))
            .toList();

    return this.rankCourses(candidateCourses, input.coverageBySkillId(), input.skillsById());
  }

  private List<String> rankCourses(
      List<Course> candidateCourses,
      Map<String, Integer> coverageBySkillId,
      Map<String, Skill> skillsById) {
    return candidateCourses.stream()
        .map(
            course ->
                new CourseRecommendationScore(
                    course,
                    this.calculateScore(
                        course, coverageBySkillId, coverageBySkillId.keySet(), skillsById)))
        .filter(courseRecommendationScore -> courseRecommendationScore.score() > 0)
        .sorted(Comparator.comparingInt(CourseRecommendationScore::score).reversed())
        .map(CourseRecommendationScore::course)
        .map(Course::getId)
        .toList();
  }

  private int calculateScore(
      Course course,
      Map<String, Integer> coverageBySkillId,
      Set<String> knownSkillIds,
      Map<String, Skill> skillsById) {
    int score = 0;

    List<String> relevantSkillIds =
        course.getSkillIdsAsString().stream().filter(knownSkillIds::contains).toList();

    if (relevantSkillIds.isEmpty()) {
      return 0;
    }

    for (String skillId : relevantSkillIds) {
      Skill skill = skillsById.get(skillId);

      if (skill == null) {
        return 0;
      }

      int userCoverage = coverageBySkillId.getOrDefault(skillId, 0);
      score += skill.getIsEssential() * (100 - userCoverage);
    }

    return score;
  }

  private boolean hasRelevantSkill(Course course, Set<String> knownSkillIds) {
    return course.getSkillIdsAsString().stream().anyMatch(knownSkillIds::contains);
  }

  private record CourseRecommendationScore(Course course, int score) {}
}
