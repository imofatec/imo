package com.imo.backend.contexts.learning_path.recommendation.usecases;

import com.imo.backend.contexts.catalog.course.Course;
import com.imo.backend.contexts.catalog.course.http.dtos.CourseDTO;
import com.imo.backend.contexts.learning_path.recommendation.Recommendation;
import com.imo.backend.contexts.learning_path.recommendation.gateways.RecommendationDataGateway;
import com.imo.backend.contexts.learning_path.recommendation.repositories.RecommendationRepository;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class GetMyRecommendationsUseCase {
  private static final int MIN_RECOMMENDATIONS = 6;

  private final RecommendationDataGateway recommendationDataGateway;
  private final RecommendationRepository recommendationRepository;

  public GetMyRecommendationsUseCase(
      RecommendationDataGateway recommendationDataGateway,
      RecommendationRepository recommendationRepository) {
    this.recommendationDataGateway = recommendationDataGateway;
    this.recommendationRepository = recommendationRepository;
  }

  public List<CourseDTO> execute(String userId) {
    this.recommendationDataGateway.assertUserExists(userId);

    Set<String> finishedCourseIds =
        this.recommendationDataGateway.findFinishedCourseIdsByUserId(userId);
    List<String> selectedCourseIds =
        new ArrayList<>(this.loadVisibleRecommendedCourseIds(userId, finishedCourseIds));

    if (selectedCourseIds.size() < MIN_RECOMMENDATIONS) {
      selectedCourseIds.addAll(this.loadFallbackCourseIds(selectedCourseIds, finishedCourseIds));
    }

    if (selectedCourseIds.isEmpty()) {
      return List.of();
    }

    Map<String, Course> activeCoursesById = this.loadActiveCoursesById(selectedCourseIds);

    return selectedCourseIds.stream()
        .map(activeCoursesById::get)
        .filter(Objects::nonNull)
        .map(CourseDTO::fromEntity)
        .toList();
  }

  private List<String> loadVisibleRecommendedCourseIds(
      String userId, Set<String> finishedCourseIds) {
    List<String> recommendedCourseIds = this.loadRecommendedCourseIds(userId);

    if (recommendedCourseIds.isEmpty()) {
      return List.of();
    }

    Map<String, Course> activeCoursesById = this.loadActiveCoursesById(recommendedCourseIds);

    return recommendedCourseIds.stream()
        .filter(courseId -> !finishedCourseIds.contains(courseId))
        .filter(activeCoursesById::containsKey)
        .distinct()
        .toList();
  }

  private List<String> loadFallbackCourseIds(
      List<String> selectedCourseIds, Set<String> finishedCourseIds) {
    int missingRecommendations = MIN_RECOMMENDATIONS - selectedCourseIds.size();

    if (missingRecommendations <= 0) {
      return List.of();
    }

    Set<String> excludedCourseIds = new HashSet<>(finishedCourseIds);
    excludedCourseIds.addAll(selectedCourseIds);

    return this.recommendationDataGateway.findLatestActiveCourseIdsExcluding(
        List.copyOf(excludedCourseIds), missingRecommendations);
  }

  private Map<String, Course> loadActiveCoursesById(List<String> courseIds) {
    if (courseIds.isEmpty()) {
      return Map.of();
    }

    return this.recommendationDataGateway.findActiveCoursesByIds(courseIds).stream()
        .collect(Collectors.toMap(Course::getId, Function.identity()));
  }

  private List<String> loadRecommendedCourseIds(String userId) {
    return this.recommendationRepository
        .findByUserId(userId)
        .map(Recommendation::getCourseIdsAsString)
        .orElse(List.of());
  }
}
