package com.imo.backend.contexts.learning_path.recommendation.usecases;

import com.imo.backend.contexts.catalog.course.Course;
import com.imo.backend.contexts.catalog.course.repositories.CourseRepository;
import com.imo.backend.contexts.catalog.skill.Skill;
import com.imo.backend.contexts.catalog.skill.repositories.SkillRepository;
import com.imo.backend.contexts.identity.user.repositories.UserRepository;
import com.imo.backend.contexts.journey_tracking.repositories.ProgressRepository;
import com.imo.backend.contexts.learning_path.recommendation.Recommendation;
import com.imo.backend.contexts.learning_path.recommendation.lib.RecommendationEngine;
import com.imo.backend.contexts.learning_path.recommendation.lib.RecommendationInput;
import com.imo.backend.contexts.learning_path.recommendation.repositories.RecommendationRepository;
import com.imo.backend.contexts.learning_path.skill_profile.SkillProfile;
import com.imo.backend.contexts.learning_path.skill_profile.repositories.SkillProfileRepository;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class RefreshRecommendationsForUserUseCase {
  private final UserRepository userRepository;
  private final SkillProfileRepository skillProfileRepository;
  private final ProgressRepository progressRepository;
  private final CourseRepository courseRepository;
  private final SkillRepository skillRepository;
  private final RecommendationEngine recommendationEngine;
  private final RecommendationRepository recommendationRepository;

  public RefreshRecommendationsForUserUseCase(
      UserRepository userRepository,
      SkillProfileRepository skillProfileRepository,
      ProgressRepository progressRepository,
      CourseRepository courseRepository,
      SkillRepository skillRepository,
      RecommendationEngine recommendationEngine,
      RecommendationRepository recommendationRepository) {
    this.userRepository = userRepository;
    this.skillProfileRepository = skillProfileRepository;
    this.progressRepository = progressRepository;
    this.courseRepository = courseRepository;
    this.skillRepository = skillRepository;
    this.recommendationEngine = recommendationEngine;
    this.recommendationRepository = recommendationRepository;
  }

  public Recommendation execute(String userId) {
    this.userRepository.findByIdOrThrow(userId);

    Map<String, Integer> coverageBySkillId = this.loadCoverageBySkillId(userId);
    List<Course> activeCourses = this.loadActiveCourses();
    Map<String, Skill> skillsById = this.loadSkillsById(activeCourses);
    Set<String> finishedCourseIds =
        Set.copyOf(this.progressRepository.findFinishedCourseIdsByUserId(userId));
    List<String> recommendedCourseIds =
        this.recommendationEngine.rankRecommendedCourseIds(
            new RecommendationInput(
                activeCourses, skillsById, finishedCourseIds, coverageBySkillId));

    return this.recommendationRepository.upsertByUserId(userId, recommendedCourseIds);
  }

  private Map<String, Integer> loadCoverageBySkillId(String userId) {
    return this.skillProfileRepository.findAllByUserId(userId).stream()
        .collect(
            Collectors.toMap(
                SkillProfile::getSkillId, SkillProfile::getCoverage, (left, right) -> right));
  }

  private List<Course> loadActiveCourses() {
    return this.courseRepository.findAllByIsActive(true);
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
}
