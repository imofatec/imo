package com.imo.backend.e2e.domains.course;

import static org.hamcrest.Matchers.*;

import com.imo.backend.contexts.catalog.course.http.dtos.CreateCourseRequest;
import com.imo.backend.e2e.config.BaseE2ETest;
import com.imo.backend.e2e.factories.CourseTestFactory;
import com.imo.backend.e2e.helpers.E2EFlowHelper;
import com.imo.backend.e2e.utils.JsonString;
import io.restassured.http.ContentType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@Slf4j
public class CreateCourseE2ETest extends BaseE2ETest {

  private final JsonString jsonString = new JsonString();

  @Test
  @DisplayName("Deve criar um curso ja com algumas aulas")
  public void shouldCreateCourseWithLessons() {

    String token = E2EFlowHelper.createAndAuthenticateUser();

    CreateCourseRequest courseRequest = CourseTestFactory.createValidCourseWithLessons(4);
    String courseJson = this.jsonString.fromObj(courseRequest);

    log.info("Informacoes do curso criado: \n" + courseJson + "\n");

    givenBaseRequest()
        .header("Authorization", "Bearer " + token)
        .contentType(ContentType.JSON)
        .body(courseJson)
        .when()
        .post("/course")
        .then()
        .body("course.name.name", notNullValue())
        .body("course.category.name", notNullValue())
        .statusCode(anyOf(is(HttpStatus.CREATED.value()), is(HttpStatus.OK.value())));
  }
}
