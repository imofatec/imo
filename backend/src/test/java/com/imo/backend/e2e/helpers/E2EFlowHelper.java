package com.imo.backend.e2e.helpers;

import com.imo.backend.contexts.catalog.course.http.dtos.CourseDetailsDTO;
import com.imo.backend.contexts.catalog.course.http.dtos.CreateCourseRequest;
import com.imo.backend.contexts.identity.actions.inputs.CreateUserInput;
import com.imo.backend.contexts.identity.http.dtos.auth.LoginRequestDTO;
import com.imo.backend.e2e.factories.CourseTestFactory;
import com.imo.backend.e2e.factories.UserTestFactory;
import com.imo.backend.e2e.utils.JsonString;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.stream.Collectors;

import static com.imo.backend.e2e.config.BaseE2ETest.givenBaseRequest;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;


public class E2EFlowHelper {
  private static final JsonString jsonString = new JsonString();

  public static String createAndAuthenticateUser() {
    CreateUserInput user = UserTestFactory.mountValidUser();
    String jsonBody = jsonString.fromObj(user);

    givenBaseRequest()
        .body(jsonBody)
        .post("/user")
        .then()
        .statusCode(anyOf(is(HttpStatus.CREATED.value()), is(HttpStatus.OK.value())));

    LoginRequestDTO loginRequest = new LoginRequestDTO(user.email(), user.password());
    String loginJson = jsonString.fromObj(loginRequest);

    Response response = givenBaseRequest().body(loginJson).post("/user/login");
    response.then().statusCode(HttpStatus.OK.value());

    return response.jsonPath().getString("accessToken");
  }


  public static CourseDetailsDTO createCourseAndReturnDetails(String token, int lessonsCount) {
    CreateCourseRequest courseRequest = CourseTestFactory.createValidCourseWithLessons(lessonsCount);
    String jsonBody = jsonString.fromObj(courseRequest);

    Response response = givenBaseRequest()
        .header("Authorization", "Bearer " + token)
        .body(jsonBody)
        .post("/course")
        .then()
        .statusCode(anyOf(is(HttpStatus.CREATED.value()), is(HttpStatus.OK.value())))
        .extract()
        .response();

    try {
      return response.as(CourseDetailsDTO.class);
    } catch (Exception e) {
      throw new RuntimeException(
          "Erro ao desserializar CourseDetailsDTO: " + response.asString(),
          e
      );
    }
  }

  public static String markAllLessonsAsWatched(String token, CourseDetailsDTO courseDetails) {
    List<String> lessonIds = courseDetails
        .lessons()
        .stream()
        .map(lesson -> lesson.getId())
        .collect(Collectors.toList());

    String finalStatus = "";

    for (String lessonId : lessonIds) {
      Response response = givenBaseRequest()
          .header("Authorization", "Bearer " + token)
          .when()
          .put("/progress/" + lessonId)
          .then()
          .statusCode(HttpStatus.OK.value())
          .extract()
          .response();

      finalStatus = response.jsonPath().getString("status");
    }

    return finalStatus;
  }
}
