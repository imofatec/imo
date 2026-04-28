package com.imo.backend.contexts.recommendation.usecases;

import com.imo.backend.contexts.catalog.course.Course;
import com.imo.backend.contexts.catalog.course.http.dtos.CourseDTO;
import com.imo.backend.contexts.catalog.course.repositories.CourseRepository;
import com.imo.backend.contexts.identity.user.repositories.UserRepository;
import com.imo.backend.contexts.journey_tracking.repositories.ProgressRepository;
import com.imo.backend.contexts.recommendation.Recommendation;
import com.imo.backend.contexts.recommendation.repositories.RecommendationRepository;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class GetMyRecommendationsUseCase {
  private final UserRepository userRepository;
  private final RecommendationRepository recommendationRepository;
  private final ProgressRepository progressRepository;
  private final CourseRepository courseRepository;

  public GetMyRecommendationsUseCase(
      UserRepository userRepository,
      RecommendationRepository recommendationRepository,
      ProgressRepository progressRepository,
      CourseRepository courseRepository) {
    this.userRepository = userRepository;
    this.recommendationRepository = recommendationRepository;
    this.progressRepository = progressRepository;
    this.courseRepository = courseRepository;
  }

  public List<CourseDTO> execute(String userId) {
    this.userRepository.findByIdOrThrow(userId);

    List<String> recommendedCourseIds = this.loadRecommendedCourseIds(userId);

    if (recommendedCourseIds.isEmpty()) {
      return List.of();
    }

    Set<String> finishedCourseIds =
        Set.copyOf(this.progressRepository.findFinishedCourseIdsByUserId(userId));
    List<String> visibleCourseIds =
        recommendedCourseIds.stream()
            .filter(courseId -> !finishedCourseIds.contains(courseId))
            .toList();

    if (visibleCourseIds.isEmpty()) {
      return List.of();
    }

    Map<String, Course> activeCoursesById =
        this.courseRepository.findAllById(visibleCourseIds).stream()
            .filter(Course::isActive)
            .collect(Collectors.toMap(Course::getId, Function.identity()));

    return visibleCourseIds.stream()
        .map(activeCoursesById::get)
        .filter(course -> course != null)
        .map(CourseDTO::fromEntity)
        .toList();
  }

  private List<String> loadRecommendedCourseIds(String userId) {
    return this.recommendationRepository
        .findByUserId(userId)
        .map(Recommendation::getCourseIdsAsString)
        .orElse(List.of());
  }
}
