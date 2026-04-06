package com.imo.backend.e2e.domains.lesson;

import static org.hamcrest.Matchers.is;

import com.imo.backend.contexts.catalog.course.http.dtos.CourseDetailsDTO;
import com.imo.backend.contexts.catalog.lesson.http.dtos.CreateLessonRequest;
import com.imo.backend.e2e.config.BaseE2ETest;
import com.imo.backend.e2e.helpers.E2EFlowHelper;
import com.imo.backend.e2e.utils.JsonString;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@Slf4j
public class AddLessonToCourseE2ETest extends BaseE2ETest {

  private final JsonString jsonString = new JsonString();

  @Test
  @DisplayName("Deve adicionar aulas a um curso existente")
  void shouldAddLessonToExistingCourse() {
    String token = E2EFlowHelper.createAndAuthenticateUser();
    CourseDetailsDTO details = E2EFlowHelper.createCourseAndReturnDetails(token, 5);
    String courseId = details.course().id();

    CreateLessonRequest command =
        new CreateLessonRequest(
            "Curso de Rust", "Ownership", "https://youtube.com/watch?v=abc123def45");

    log.info(
        "Course ID: {}\nCourse details:\n\nContributor Id: {}\nDescription: {}\n{}",
        courseId,
        details.course().description());

    givenBaseRequest()
        .header("Authorization", "Bearer " + token)
        .body(jsonString.fromObj(command))
        .when()
        .post("/lesson/" + courseId)
        .then()
        .statusCode(HttpStatus.CREATED.value())
        .body("title", is("Curso de Rust"));
  }
}
