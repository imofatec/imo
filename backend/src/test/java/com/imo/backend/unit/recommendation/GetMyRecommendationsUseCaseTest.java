package com.imo.backend.unit.recommendation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.imo.backend.contexts.catalog.course.Course;
import com.imo.backend.contexts.catalog.course.http.dtos.CourseDTO;
import com.imo.backend.contexts.catalog.course.repositories.CourseRepository;
import com.imo.backend.contexts.catalog.course.value_objects.Categories;
import com.imo.backend.contexts.identity.user.User;
import com.imo.backend.contexts.identity.user.repositories.UserRepository;
import com.imo.backend.contexts.journey_tracking.repositories.ProgressRepository;
import com.imo.backend.contexts.recommendation.Recommendation;
import com.imo.backend.contexts.recommendation.repositories.RecommendationRepository;
import com.imo.backend.contexts.recommendation.usecases.GetMyRecommendationsUseCase;
import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetMyRecommendationsUseCaseTest {
  @Mock private UserRepository userRepository;
  @Mock private RecommendationRepository recommendationRepository;
  @Mock private ProgressRepository progressRepository;
  @Mock private CourseRepository courseRepository;

  @InjectMocks private GetMyRecommendationsUseCase useCase;

  @Test
  @DisplayName(
      "happy path (execute): filtrar cursos concluídos e inativos preservando a ordem do cache")
  void shouldFilterFinishedAndInactiveCoursesPreservingOrder() {
    String userId = new ObjectId().toString();
    String contributorId = new ObjectId().toString();
    String courseIdA = new ObjectId().toString();
    String courseIdB = new ObjectId().toString();
    String courseIdC = new ObjectId().toString();

    User user = new User("Teste", "teste@email.com", "Senha123", true);

    Recommendation recommendation =
        new Recommendation(userId, List.of(courseIdA, courseIdB, courseIdC));

    Course activeCourse =
        new Course(
            true,
            contributorId,
            "Curso Ativo Valido",
            "Iniciante",
            Categories.DEV_WEB,
            "Descrição do curso",
            "https://www.youtube.com/watch?v=abc123XYZ89",
            1,
            List.of(new ObjectId().toString()));
    activeCourse.setId(courseIdA);

    Course inactiveCourse =
        new Course(
            false,
            contributorId,
            "Curso Inativo Valido",
            "Iniciante",
            Categories.DEV_WEB,
            "Descrição do curso",
            "https://www.youtube.com/watch?v=abc123XYZ89",
            1,
            List.of(new ObjectId().toString()));
    inactiveCourse.setId(courseIdC);

    when(this.userRepository.findByIdOrThrow(userId)).thenReturn(user);
    when(this.recommendationRepository.findByUserId(userId))
        .thenReturn(Optional.of(recommendation));
    when(this.progressRepository.findFinishedCourseIdsByUserId(userId))
        .thenReturn(List.of(courseIdB));
    when(this.courseRepository.findAllById(List.of(courseIdA, courseIdC)))
        .thenReturn(List.of(activeCourse, inactiveCourse));

    List<CourseDTO> response = this.useCase.execute(userId);

    assertEquals(1, response.size());
    assertEquals(courseIdA, response.getFirst().id());
  }
}
