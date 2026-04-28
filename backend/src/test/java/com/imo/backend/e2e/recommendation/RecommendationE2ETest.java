package com.imo.backend.e2e.recommendation;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.imo.backend.contexts.catalog.course.http.dtos.CourseDTO;
import com.imo.backend.contexts.catalog.course.http.dtos.CourseDetailsDTO;
import com.imo.backend.contexts.catalog.course.http.dtos.UpdateCourseByIdRequest;
import com.imo.backend.contexts.catalog.course.value_objects.Categories;
import com.imo.backend.contexts.identity.user.repositories.UserRepository;
import com.imo.backend.contexts.recommendation.repositories.RecommendationRepository;
import com.imo.backend.e2e.BaseE2ETest;
import com.imo.backend.e2e.catalog.helpers.CatalogSkillTestHelper;
import com.imo.backend.e2e.catalog.helpers.CatalogTestHelper;
import com.imo.backend.e2e.catalog.helpers.CatalogTestHelper.TestCourse;
import com.imo.backend.e2e.identity.helpers.IdentityTestHelper;
import com.imo.backend.e2e.identity.helpers.IdentityTestHelper.TestUser;
import com.imo.backend.e2e.journey_tracking.helpers.JourneyTrackingTestHelper;
import com.imo.backend.e2e.recommendation.helpers.RecommendationTestHelper;
import io.restassured.http.ContentType;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

class RecommendationE2ETest extends BaseE2ETest {
  @Autowired private UserRepository userRepository;

  @Autowired private RecommendationRepository recommendationRepository;

  @Test
  @DisplayName(
      "happy path (GET /api/recommendation/me): retorna 200 com cursos recomendados sem incluir os concluídos")
  void shouldGetMyRecommendations() {
    TestUser user = TestUser.defaultUser();
    String token = IdentityTestHelper.registerAndLogin(user);

    TestCourse finishedCourseFixture = TestCourse.defaultCourse();
    TestCourse recommendedCourseFixture =
        new TestCourse(
            "Curso de CSS avançado",
            finishedCourseFixture.category(),
            finishedCourseFixture.level(),
            "Curso recomendado para continuar evoluindo",
            finishedCourseFixture.lessons(),
            finishedCourseFixture.skillIds());

    CourseDetailsDTO finishedCourse = CatalogTestHelper.createCourse(token, finishedCourseFixture);
    CourseDetailsDTO recommendedCourse =
        CatalogTestHelper.createCourse(token, recommendedCourseFixture);
    String lessonId = finishedCourse.lessons().getFirst().getId();
    String userId = this.userRepository.findByEmail(user.email()).orElseThrow().getId();

    JourneyTrackingTestHelper.watchLesson(token, lessonId);
    RecommendationTestHelper.waitForRecommendation(this.recommendationRepository, userId, 1);

    List<CourseDTO> recommendations = RecommendationTestHelper.getMyRecommendations(token);

    assertFalse(recommendations.isEmpty());
    assertEquals(1, recommendations.size());
    assertEquals(recommendedCourse.course().id(), recommendations.getFirst().id());
    assertTrue(
        recommendations.stream()
            .noneMatch(course -> course.id().equals(finishedCourse.course().id())));
  }

  @Test
  @DisplayName(
      "functional (GET /api/recommendation/me): incluir novo curso relevante após mudança no catálogo")
  void shouldRefreshRecommendationsWhenRelevantCourseIsCreated() {
    TestUser user = TestUser.defaultUser();
    String token = IdentityTestHelper.registerAndLogin(user);

    TestCourse finishedCourseFixture = TestCourse.defaultCourse();
    CourseDetailsDTO finishedCourse = CatalogTestHelper.createCourse(token, finishedCourseFixture);
    String lessonId = finishedCourse.lessons().getFirst().getId();
    String userId = this.userRepository.findByEmail(user.email()).orElseThrow().getId();

    JourneyTrackingTestHelper.watchLesson(token, lessonId);
    RecommendationTestHelper.waitForRecommendation(this.recommendationRepository, userId, 0);

    List<CourseDTO> recommendationsBefore = RecommendationTestHelper.getMyRecommendations(token);
    assertTrue(recommendationsBefore.isEmpty());

    TestCourse newlyRelevantCourseFixture =
        new TestCourse(
            "Curso novo de HTML e CSS",
            finishedCourseFixture.category(),
            finishedCourseFixture.level(),
            "Curso novo para recomendar",
            finishedCourseFixture.lessons(),
            finishedCourseFixture.skillIds());
    CourseDetailsDTO newlyRelevantCourse =
        CatalogTestHelper.createCourse(token, newlyRelevantCourseFixture);

    RecommendationTestHelper.waitForRecommendation(this.recommendationRepository, userId, 1);
    List<CourseDTO> recommendationsAfter = RecommendationTestHelper.getMyRecommendations(token);

    assertEquals(1, recommendationsAfter.size());
    assertEquals(newlyRelevantCourse.course().id(), recommendationsAfter.getFirst().id());
  }

  @Test
  @DisplayName(
      "functional (GET /api/recommendation/me): remover curso recomendado quando skills do curso mudam")
  void shouldRefreshRecommendationsWhenCourseSkillsChange() {
    TestUser user = TestUser.defaultUser();
    String token = IdentityTestHelper.registerAndLogin(user);

    TestCourse finishedCourseFixture = TestCourse.defaultCourse();
    TestCourse recommendedCourseFixture =
        new TestCourse(
            "Curso recomendado de frontend",
            finishedCourseFixture.category(),
            finishedCourseFixture.level(),
            "Curso para ser removido da recommendation",
            finishedCourseFixture.lessons(),
            finishedCourseFixture.skillIds());

    CourseDetailsDTO finishedCourse = CatalogTestHelper.createCourse(token, finishedCourseFixture);
    CourseDetailsDTO recommendedCourse =
        CatalogTestHelper.createCourse(token, recommendedCourseFixture);
    String lessonId = finishedCourse.lessons().getFirst().getId();
    String userId = this.userRepository.findByEmail(user.email()).orElseThrow().getId();

    JourneyTrackingTestHelper.watchLesson(token, lessonId);
    RecommendationTestHelper.waitForRecommendation(this.recommendationRepository, userId, 1);

    List<String> updatedSkillIds =
        CatalogSkillTestHelper.getSkillIdsForCategory(Categories.DATA, 2);
    UpdateCourseByIdRequest updateRequest =
        new UpdateCourseByIdRequest(
            "Curso migrado para dados",
            Categories.DATA,
            "Intermediário",
            "Curso alterado para outra trilha",
            updatedSkillIds);

    given()
        .header("Authorization", "Bearer " + token)
        .contentType(ContentType.JSON)
        .body(updateRequest)
        .when()
        .put("/api/course/{id}", recommendedCourse.course().id())
        .then()
        .statusCode(HttpStatus.OK.value());

    RecommendationTestHelper.waitForRecommendation(this.recommendationRepository, userId, 0);
    List<CourseDTO> recommendationsAfter = RecommendationTestHelper.getMyRecommendations(token);

    assertTrue(recommendationsAfter.isEmpty());
  }

  @Test
  @DisplayName("exception (GET /api/recommendation/me): retorna 401 quando não autenticado")
  void shouldReturn401WhenGettingMyRecommendationsNotAuthenticated() {
    given()
        .when()
        .get("/api/recommendation/me")
        .then()
        .statusCode(HttpStatus.UNAUTHORIZED.value())
        .contentType(ContentType.JSON)
        .body("error", equalTo("UNAUTHORIZED"));
  }
}
