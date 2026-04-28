package com.imo.backend.contexts.recommendation.usecases;

import com.imo.backend.contexts.catalog.course.Course;
import com.imo.backend.contexts.catalog.course.repositories.CourseRepository;
import com.imo.backend.contexts.catalog.course.repositories.CourseSearchParams;
import com.imo.backend.contexts.catalog.skill.Skill;
import com.imo.backend.contexts.catalog.skill.repositories.SkillRepository;
import com.imo.backend.contexts.common.CombineWith;
import com.imo.backend.contexts.common.MatchType;
import com.imo.backend.contexts.identity.user.repositories.UserRepository;
import com.imo.backend.contexts.journey_tracking.repositories.ProgressRepository;
import com.imo.backend.contexts.recommendation.Recommendation;
import com.imo.backend.contexts.recommendation.repositories.RecommendationRepository;
import com.imo.backend.contexts.skill_profile.SkillProfile;
import com.imo.backend.contexts.skill_profile.repositories.SkillProfileRepository;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class RefreshRecommendationsForUserUseCase {
  private static final CourseSearchParams EMPTY_COURSE_SEARCH_PARAMS =
      new CourseSearchParams(null, null, null, null, null);

  private final UserRepository userRepository;
  private final SkillProfileRepository skillProfileRepository;
  private final ProgressRepository progressRepository;
  private final CourseRepository courseRepository;
  private final SkillRepository skillRepository;
  private final RecommendationRepository recommendationRepository;

  public RefreshRecommendationsForUserUseCase(
      UserRepository userRepository,
      SkillProfileRepository skillProfileRepository,
      ProgressRepository progressRepository,
      CourseRepository courseRepository,
      SkillRepository skillRepository,
      RecommendationRepository recommendationRepository) {
    this.userRepository = userRepository;
    this.skillProfileRepository = skillProfileRepository;
    this.progressRepository = progressRepository;
    this.courseRepository = courseRepository;
    this.skillRepository = skillRepository;
    this.recommendationRepository = recommendationRepository;
  }

  public Recommendation execute(String userId) {
    this.userRepository.findByIdOrThrow(userId);

    Map<String, Integer> coverageBySkillId = this.loadCoverageBySkillId(userId);

    if (coverageBySkillId.isEmpty()) {
      return this.recommendationRepository.upsertByUserId(userId, List.of());
    }

    Set<String> finishedCourseIds =
        Set.copyOf(this.progressRepository.findFinishedCourseIdsByUserId(userId));

    List<Course> candidateCourses =
        this.loadCandidateCourses(finishedCourseIds, coverageBySkillId.keySet());

    Map<String, Skill> skillsById = this.loadSkillsById(candidateCourses);

    List<String> recommendedCourseIds =
        this.rankCourses(candidateCourses, coverageBySkillId, skillsById);

    return this.recommendationRepository.upsertByUserId(userId, recommendedCourseIds);
  }

  private Map<String, Integer> loadCoverageBySkillId(String userId) {
    return this.skillProfileRepository.findAllByUserId(userId).stream()
        .collect(
            Collectors.toMap(
                SkillProfile::getSkillId, SkillProfile::getCoverage, (left, right) -> right));
  }

  private List<Course> loadCandidateCourses(
      Set<String> finishedCourseIds, Set<String> knownSkillIds) {
    return this.courseRepository
        .search(EMPTY_COURSE_SEARCH_PARAMS, MatchType.PERFECT, CombineWith.AND, true)
        .stream()
        .filter(course -> !finishedCourseIds.contains(course.getId()))
        .filter(course -> this.hasRelevantSkill(course, knownSkillIds))
        .toList();
  }

  private Map<String, Skill> loadSkillsById(List<Course> courses) {
    Set<String> skillIds =
        courses.stream()
            .flatMap(course -> course.getSkillIdsAsString().stream())
            .collect(Collectors.toSet());

    if (skillIds.isEmpty()) {
      return Map.of();
    }

    return this.skillRepository.findAllById(skillIds).stream()
        .collect(Collectors.toMap(Skill::getId, Function.identity()));
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
