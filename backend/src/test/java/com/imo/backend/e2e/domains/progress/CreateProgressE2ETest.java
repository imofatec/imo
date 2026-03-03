package com.imo.backend.e2e.domains.progress;

import com.imo.backend.contexts.catalog.course.http.dtos.CourseDetailsDTO;
import com.imo.backend.e2e.config.BaseE2ETest;
import com.imo.backend.e2e.helpers.E2EFlowHelper;
import io.restassured.http.ContentType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.Assert.assertEquals;


@Slf4j
public class CreateProgressE2ETest extends BaseE2ETest {

  @Test
  @DisplayName("Deve criar progresso ao assistir a primeira aula")
  public void shouldCreateProgressWhenWatchingFirstLesson() {
    String token = E2EFlowHelper.createAndAuthenticateUser();
    CourseDetailsDTO courseDetailsDTO = E2EFlowHelper.createCourseAndReturnDetails(token, 3);
    String firstLessonId = courseDetailsDTO.lessons().get(0).getId();

    var response = givenBaseRequest()
        .header("Authorization", "Bearer " + token)
        .when()
        .put("/progress/" + firstLessonId)
        .then()
        .statusCode(HttpStatus.OK.value())
        .extract()
        .response();

    var status = response.jsonPath().getString("status");
    log.info("Status do progresso iniciado na aula com ID: " + response.jsonPath().getString("id"));
    assertEquals("IN_PROGRESS", status);
  }


  @Test
  @DisplayName("Deve iniciar e finalizar um progresso completo do curso")
  public void shouldInitAndFinishACourseProgress() {

    String token = E2EFlowHelper.createAndAuthenticateUser();
    CourseDetailsDTO courseDetailsDTO = E2EFlowHelper.createCourseAndReturnDetails(token, 5);
    String finalStatus = E2EFlowHelper.markAllLessonsAsWatched(token, courseDetailsDTO);

    log.info("Status final do progresso após marcar todas as aulas: {}", finalStatus);

    assertEquals("FINISHED", finalStatus);
  }

  @Test
  @DisplayName("Marcar a mesma aula duas vezes deve retornar 409")
  public void shouldReturnConflictWhenMarkingSameLessonTwice() {
    String token = E2EFlowHelper.createAndAuthenticateUser();
    CourseDetailsDTO courseDetails = E2EFlowHelper.createCourseAndReturnDetails(token, 2);
    String lessonId = courseDetails.lessons().get(0).getId();

    givenBaseRequest()
        .header("Authorization", "Bearer " + token)
        .when()
        .put("/progress/" + lessonId)
        .then()
        .statusCode(HttpStatus.OK.value());

    givenBaseRequest()
        .header("Authorization", "Bearer " + token)
        .when()
        .put("/progress/" + lessonId)
        .then()
        .statusCode(HttpStatus.CONFLICT.value());
  }

  @Test
  @DisplayName("Chamada sem autenticação deve retornar 401")
  public void shouldReturnUnauthorizedWhenNoAuth() {
    String token = E2EFlowHelper.createAndAuthenticateUser();
    CourseDetailsDTO courseDetails = E2EFlowHelper.createCourseAndReturnDetails(token, 1);
    String lessonId = courseDetails.lessons().get(0).getId();

    givenBaseRequest()
        .contentType(ContentType.JSON)
        .when()
        .put("/progress/" + lessonId)
        .then()
        .statusCode(HttpStatus.UNAUTHORIZED.value());
  }

  @Test
  @DisplayName("Chamada com lessonId inválido deve retornar 400")
  public void shouldReturnBadRequestWhenInvalidLessonId() {
    String token = E2EFlowHelper.createAndAuthenticateUser();

    var response = givenBaseRequest()
        .header("Authorization", "Bearer " + token)
        .when()
        .put("/progress/invalid-id")
        .then()
        .statusCode(HttpStatus.BAD_REQUEST.value());


    log.info(response.toString());
  }
}
