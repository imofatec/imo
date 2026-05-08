package com.imo.backend.e2e.journey_tracking;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.imo.backend.contexts.catalog.course.http.dtos.CourseDetailsDTO;
import com.imo.backend.contexts.catalog.course.http.dtos.UpdateCourseByIdRequest;
import com.imo.backend.contexts.catalog.course.value_objects.Categories;
import com.imo.backend.contexts.journey_tracking.progress_milestone.http.dtos.ProgressMilestoneDTO;
import com.imo.backend.e2e.BaseE2ETest;
import com.imo.backend.e2e.catalog.helpers.CatalogSkillTestHelper;
import com.imo.backend.e2e.catalog.helpers.CatalogTestHelper;
import com.imo.backend.e2e.catalog.helpers.CatalogTestHelper.TestCourse;
import com.imo.backend.e2e.identity.helpers.IdentityTestHelper;
import com.imo.backend.e2e.identity.helpers.IdentityTestHelper.TestUser;
import com.imo.backend.e2e.journey_tracking.helpers.JourneyTrackingTestHelper;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class ProgressMilestoneE2ETest extends BaseE2ETest {
  @Test
  @DisplayName(
      "happy path (PUT/GET /api/progress/milestones + GET /m/{code}): cria marco público, imagem PNG e página compartilhável")
  void shouldCreateAndExposePublicProgressMilestone() {
    String token = IdentityTestHelper.registerAndLogin(TestUser.defaultUser());
    CourseDetailsDTO courseDetails =
        CatalogTestHelper.createCourse(token, TestCourse.defaultCourseWithThreeLessons());

    this.watchAllLessons(token, courseDetails);

    ProgressMilestoneDTO createdMilestone =
        given()
            .header("Authorization", "Bearer " + token)
            .when()
            .put("/api/progress/milestones/course/{courseId}", courseDetails.course().id())
            .then()
            .statusCode(HttpStatus.OK.value())
            .contentType(ContentType.JSON)
            .extract()
            .as(ProgressMilestoneDTO.class);

    assertNotNull(createdMilestone.id());
    assertEquals(courseDetails.course().name().name(), createdMilestone.courseName());
    assertEquals(3, createdMilestone.watchedLessonsCount());
    assertEquals(3, createdMilestone.totalLessonsCount());
    assertEquals(100, createdMilestone.completionPercentage());

    given()
        .when()
        .get("/api/progress/milestones/public/{publicCode}", createdMilestone.publicCode())
        .then()
        .statusCode(HttpStatus.OK.value())
        .contentType(ContentType.JSON)
        .body("publicCode", equalTo(createdMilestone.publicCode()))
        .body("courseName", equalTo(createdMilestone.courseName()))
        .body("shareUrl", containsString("/m/" + createdMilestone.publicCode()))
        .body("imageUrl", containsString("/image.png"));

    given()
        .when()
        .get(
            "/api/progress/milestones/public/{publicCode}/image.png", createdMilestone.publicCode())
        .then()
        .statusCode(HttpStatus.OK.value())
        .contentType("image/png");

    given()
        .when()
        .get("/m/{publicCode}", createdMilestone.publicCode())
        .then()
        .statusCode(HttpStatus.OK.value())
        .contentType(containsString("text/html"))
        .body(containsString(createdMilestone.courseName()))
        .body(containsString(createdMilestone.publicCode()))
        .body(containsString("og:image"));
  }

  @Test
  @DisplayName(
      "happy path (PUT /api/progress/milestones/course/{courseId}): atualiza o snapshot quando o curso é alterado")
  void shouldRefreshSnapshotWhenCourseChanges() {
    String token = IdentityTestHelper.registerAndLogin(TestUser.defaultUser());
    CourseDetailsDTO courseDetails =
        CatalogTestHelper.createCourse(token, TestCourse.defaultCourseWithThreeLessons());

    this.watchAllLessons(token, courseDetails);

    ProgressMilestoneDTO firstMilestone =
        given()
            .header("Authorization", "Bearer " + token)
            .when()
            .put("/api/progress/milestones/course/{courseId}", courseDetails.course().id())
            .then()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .as(ProgressMilestoneDTO.class);

    given()
        .header("Authorization", "Bearer " + token)
        .contentType(ContentType.JSON)
        .body(
            new UpdateCourseByIdRequest(
                "Curso Atualizado para Compartilhamento",
                Categories.DEV_WEB,
                "Iniciante",
                "Descrição suficiente para atualizar o snapshot do marco de progresso.",
                CatalogSkillTestHelper.getSkillIdsForCategory(Categories.DEV_WEB, 2)))
        .when()
        .put("/api/course/{id}", courseDetails.course().id())
        .then()
        .statusCode(HttpStatus.OK.value());

    ProgressMilestoneDTO refreshedMilestone =
        given()
            .header("Authorization", "Bearer " + token)
            .when()
            .put("/api/progress/milestones/course/{courseId}", courseDetails.course().id())
            .then()
            .statusCode(HttpStatus.OK.value())
            .contentType(ContentType.JSON)
            .extract()
            .as(ProgressMilestoneDTO.class);

    assertEquals(firstMilestone.id(), refreshedMilestone.id());
    assertEquals(firstMilestone.publicCode(), refreshedMilestone.publicCode());
    assertEquals("Curso Atualizado para Compartilhamento", refreshedMilestone.courseName());
  }

  @Test
  @DisplayName(
      "exception (PUT /api/progress/milestones/course/{courseId}): retorna 403 quando o curso não foi concluído")
  void shouldReturn403WhenCourseIsNotFinished() {
    String token = IdentityTestHelper.registerAndLogin(TestUser.defaultUser());
    CourseDetailsDTO courseDetails =
        CatalogTestHelper.createCourse(token, TestCourse.defaultCourseWithThreeLessons());

    JourneyTrackingTestHelper.watchLesson(token, courseDetails.lessons().getFirst().getId());

    given()
        .header("Authorization", "Bearer " + token)
        .when()
        .put("/api/progress/milestones/course/{courseId}", courseDetails.course().id())
        .then()
        .statusCode(HttpStatus.FORBIDDEN.value())
        .contentType(ContentType.JSON)
        .body("error", equalTo("FORBIDDEN"));
  }

  @Test
  @DisplayName(
      "exception (GET /api/progress/milestones/public/{publicCode}): retorna 404 quando o marco não existe")
  void shouldReturn404WhenMilestoneDoesNotExist() {
    given()
        .when()
        .get("/api/progress/milestones/public/{publicCode}", "missing-milestone-code")
        .then()
        .statusCode(HttpStatus.NOT_FOUND.value())
        .contentType(ContentType.JSON)
        .body("error", equalTo("NOT_FOUND"));
  }

  private void watchAllLessons(String token, CourseDetailsDTO courseDetails) {
    courseDetails
        .lessons()
        .forEach(lesson -> JourneyTrackingTestHelper.watchLesson(token, lesson.getId()));
  }
}
