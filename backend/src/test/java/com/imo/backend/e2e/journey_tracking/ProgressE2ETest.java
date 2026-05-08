package com.imo.backend.e2e.journey_tracking;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

import com.imo.backend.contexts.catalog.course.http.dtos.CourseDetailsDTO;
import com.imo.backend.contexts.identity.user.repositories.UserRepository;
import com.imo.backend.contexts.journey_tracking.progress.ProgressStatus;
import com.imo.backend.contexts.journey_tracking.progress.controllers.dtos.ProgressDTO;
import com.imo.backend.contexts.journey_tracking.progress.controllers.dtos.ProgressDetailsDTO;
import com.imo.backend.contexts.learning_path.skill_profile.SkillProfile;
import com.imo.backend.contexts.learning_path.skill_profile.repositories.SkillProfileRepository;
import com.imo.backend.e2e.BaseE2ETest;
import com.imo.backend.e2e.catalog.helpers.CatalogTestHelper;
import com.imo.backend.e2e.catalog.helpers.CatalogTestHelper.TestCourse;
import com.imo.backend.e2e.identity.helpers.IdentityTestHelper;
import com.imo.backend.e2e.identity.helpers.IdentityTestHelper.TestUser;
import com.imo.backend.e2e.journey_tracking.helpers.JourneyTrackingTestHelper;
import com.imo.backend.e2e.skill_profile.helpers.SkillProfileTestHelper;
import io.restassured.http.ContentType;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

class ProgressE2ETest extends BaseE2ETest {
  @Autowired private UserRepository userRepository;

  @Autowired private SkillProfileRepository skillProfileRepository;

  @Test
  @DisplayName(
      "happy path (GET /api/progress/details): retorna 200 com lista de progressos paginada")
  void shouldGetAllProgress() {
    String token = IdentityTestHelper.registerAndLogin(TestUser.defaultUser());
    CourseDetailsDTO courseDetails =
        CatalogTestHelper.createCourse(token, TestCourse.defaultCourse());
    String lessonId = courseDetails.lessons().getFirst().getId();
    JourneyTrackingTestHelper.watchLesson(token, lessonId);

    given()
        .header("Authorization", "Bearer " + token)
        .queryParam("page", 0)
        .queryParam("size", 10)
        .when()
        .get("/api/progress/details")
        .then()
        .statusCode(HttpStatus.OK.value())
        .contentType(ContentType.JSON)
        .body("items.size()", equalTo(1))
        .body("pagination.currentPage", equalTo(0))
        .body("pagination.remainingPages", equalTo(0));
  }

  @Test
  @DisplayName("exception (GET /api/progress/details): retorna 401 quando não autenticado")
  void shouldReturn401WhenGettingAllProgressNotAuthenticated() {
    given()
        .queryParam("page", 0)
        .queryParam("size", 10)
        .when()
        .get("/api/progress/details")
        .then()
        .statusCode(HttpStatus.UNAUTHORIZED.value())
        .body("error", equalTo("UNAUTHORIZED"));
  }

  @Test
  @DisplayName(
      "happy path (GET /api/progress/details/{courseId}): retorna 200 com detalhes do progresso")
  void shouldGetProgressByCourseId() {
    String token = IdentityTestHelper.registerAndLogin(TestUser.defaultUser());
    CourseDetailsDTO courseDetails =
        CatalogTestHelper.createCourse(token, TestCourse.defaultCourse());
    String lessonId = courseDetails.lessons().getFirst().getId();
    JourneyTrackingTestHelper.watchLesson(token, lessonId);

    ProgressDetailsDTO progressDetails =
        given()
            .header("Authorization", "Bearer " + token)
            .when()
            .get("/api/progress/details/{courseId}", courseDetails.course().id())
            .then()
            .statusCode(HttpStatus.OK.value())
            .contentType(ContentType.JSON)
            .extract()
            .as(ProgressDetailsDTO.class);

    assertNotNull(progressDetails.progress().id());
    assertEquals(courseDetails.course().id(), progressDetails.course().id());
    assertEquals(1, progressDetails.progress().lessonsWatched().size());
    assertTrue(progressDetails.progress().lessonsWatched().contains(lessonId));
    assertEquals(1, progressDetails.summary().watchedLessonsCount());
    assertEquals(
        courseDetails.course().lessonsCount(), progressDetails.summary().totalLessonsCount());
    assertEquals(
        100 / courseDetails.course().lessonsCount(),
        progressDetails.summary().completionPercentage());
  }

  @Test
  @DisplayName(
      "exception (GET /api/progress/details/{courseId}): retorna 401 quando não autenticado")
  void shouldReturn401WhenGettingProgressByCourseIdNotAuthenticated() {
    String token = IdentityTestHelper.registerAndLogin(TestUser.defaultUser());
    CourseDetailsDTO courseDetails =
        CatalogTestHelper.createCourse(token, TestCourse.defaultCourse());

    given()
        .when()
        .get("/api/progress/details/{courseId}", courseDetails.course().id())
        .then()
        .statusCode(HttpStatus.UNAUTHORIZED.value())
        .body("error", equalTo("UNAUTHORIZED"));
  }

  @Test
  @DisplayName(
      "exception (GET /api/progress/details/{courseId}): retorna 400 quando courseId é inválido")
  void shouldReturn400WhenCourseIdIsInvalid() {
    String token = IdentityTestHelper.registerAndLogin(TestUser.defaultUser());

    given()
        .header("Authorization", "Bearer " + token)
        .when()
        .get("/api/progress/details/{courseId}", "invalid-id")
        .then()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .body("error", equalTo("BAD_REQUEST"));
  }

  @Test
  @DisplayName(
      "happy path (PUT /api/progress/{lessonId}): retorna 200 quando marca aula como assistida")
  void shouldWatchLesson() {
    String token = IdentityTestHelper.registerAndLogin(TestUser.defaultUser());
    CourseDetailsDTO courseDetails =
        CatalogTestHelper.createCourse(token, TestCourse.defaultCourseWithThreeLessons());
    String lessonId = courseDetails.lessons().getFirst().getId();

    ProgressDTO progress =
        given()
            .header("Authorization", "Bearer " + token)
            .when()
            .put("/api/progress/{lessonId}", lessonId)
            .then()
            .statusCode(HttpStatus.OK.value())
            .contentType(ContentType.JSON)
            .extract()
            .as(ProgressDTO.class);

    assertNotNull(progress.id());
    assertEquals(1, progress.lessonsWatched().size());
    assertTrue(progress.lessonsWatched().contains(lessonId));
    assertEquals(ProgressStatus.IN_PROGRESS, progress.status());
    assertNotNull(progress.progressPeriod().startedAt());
    assertNull(progress.progressPeriod().finishedAt());
  }

  @Test
  @DisplayName("exception (PUT /api/progress/{lessonId}): retorna 401 quando não autenticado")
  void shouldReturn401WhenWatchingLessonNotAuthenticated() {
    String token = IdentityTestHelper.registerAndLogin(TestUser.defaultUser());
    CourseDetailsDTO courseDetails =
        CatalogTestHelper.createCourse(token, TestCourse.defaultCourse());
    String lessonId = courseDetails.lessons().getFirst().getId();

    given()
        .when()
        .put("/api/progress/{lessonId}", lessonId)
        .then()
        .statusCode(HttpStatus.UNAUTHORIZED.value())
        .body("error", equalTo("UNAUTHORIZED"));
  }

  @Test
  @DisplayName("exception (PUT /api/progress/{lessonId}): retorna 409 quando aula já foi assistida")
  void shouldReturn409WhenLessonAlreadyWatched() {
    String token = IdentityTestHelper.registerAndLogin(TestUser.defaultUser());

    TestCourse courseWithThreeLessons = TestCourse.defaultCourseWithThreeLessons();
    CourseDetailsDTO courseDetails = CatalogTestHelper.createCourse(token, courseWithThreeLessons);
    String firstLessonId = courseDetails.lessons().getFirst().getId();
    JourneyTrackingTestHelper.watchLesson(token, firstLessonId);

    given()
        .header("Authorization", "Bearer " + token)
        .when()
        .put("/api/progress/{lessonId}", firstLessonId)
        .then()
        .statusCode(HttpStatus.CONFLICT.value())
        .body("error", equalTo("CONFLICT"));
  }

  @Test
  @DisplayName(
      "functional (PUT /api/progress/{lessonId}): atualizar skillProfile quando usuário finaliza curso")
  void shouldUpdateSkillProfileWhenCourseIsFinished() {
    TestUser user = TestUser.defaultUser();
    String token = IdentityTestHelper.registerAndLogin(user);
    CourseDetailsDTO courseDetails =
        CatalogTestHelper.createCourse(token, TestCourse.defaultCourse());
    String lessonId = courseDetails.lessons().getFirst().getId();
    String userId = this.userRepository.findByEmail(user.email()).orElseThrow().getId();

    JourneyTrackingTestHelper.watchLesson(token, lessonId);

    List<SkillProfile> skillProfiles =
        SkillProfileTestHelper.waitForSkillProfiles(
            this.skillProfileRepository, userId, courseDetails.course().skillIds().size());

    assertEquals(courseDetails.course().skillIds().size(), skillProfiles.size());
    assertTrue(
        skillProfiles.stream()
            .allMatch(
                skillProfile ->
                    courseDetails.course().skillIds().contains(skillProfile.getSkillId())));
  }
}
