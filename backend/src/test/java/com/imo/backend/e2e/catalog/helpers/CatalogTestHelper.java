package com.imo.backend.e2e.catalog.helpers;

import com.imo.backend.contexts.catalog.course.http.dtos.CourseDTO;
import com.imo.backend.contexts.catalog.course.http.dtos.CourseDetailsDTO;
import com.imo.backend.contexts.catalog.course.http.dtos.CreateCourseRequest;
import com.imo.backend.contexts.catalog.course.value_objects.Categories;
import com.imo.backend.contexts.catalog.lesson.http.dtos.CreateLessonRequest;
import com.imo.backend.contexts.catalog.lesson.http.dtos.LessonDTO;
import io.restassured.http.ContentType;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public final class CatalogTestHelper {

  public record TestCourse(
      String name,
      Categories category,
      String level,
      String description,
      List<CreateLessonRequest> lessons
  ) {
    public static TestCourse defaultCourse() {
      return new TestCourse(
          "Curso de Java Completo",
          Categories.DEV_WEB,
          "Iniciante",
          "Um curso completo para aprender Java do zero",
          List.of(TestLesson.defaultLesson().toCreateRequest())
      );
    }

    public static TestCourse defaultCourseWithThreeLessons() {
      String[] youtubeVideoIds = {
          "rnZmWZgPB7I", "yMztOcYgtLI", "Ps0zSZXCDPk"
      };

      int numberOfLessons = youtubeVideoIds.length;

      List<CreateLessonRequest> lessons = new ArrayList<>();
      for (int i = 0; i < numberOfLessons; i++) {
        lessons.add(new CreateLessonRequest(
            "Aula " + (i + 1) + " do Curso",
            "Descrição da aula " + (i + 1),
            youtubeVideoIds[i]
        ));
      }

      return new TestCourse(
          "Curso com " + numberOfLessons + " Aulas",
          Categories.DEV_WEB,
          "Iniciante",
          "Um curso com " + numberOfLessons + " aulas para teste",
          lessons
      );
    }

    public CreateCourseRequest toCreateRequest() {
      return new CreateCourseRequest(name, category, level, description, lessons);
    }
  }

  public record TestLesson(
      String title,
      String description,
      String youtubeLink
  ) {
    public static TestLesson defaultLesson() {
      return new TestLesson(
          "Introdução ao curso",
          "Aula introdutória do curso",
          "https://www.youtube.com/watch?v=hO3EH6A-SEE&list=OLAK5uy_nnKPUsEOrSbpD4JRcSyi68p77fxhiTUJk&index=2"
      );
    }

    public CreateLessonRequest toCreateRequest() {
      return new CreateLessonRequest(title, description, youtubeLink);
    }
  }

  public static CourseDetailsDTO createCourse(String token, TestCourse testCourse) {
    return given()
        .header("Authorization", "Bearer " + token)
        .contentType(ContentType.JSON)
        .body(testCourse.toCreateRequest())
        .when()
        .post("/api/course")
        .then()
        .extract()
        .as(CourseDetailsDTO.class);
  }

  public static LessonDTO createLesson(String token, String courseId, TestLesson testLesson) {
    return given()
        .header("Authorization", "Bearer " + token)
        .contentType(ContentType.JSON)
        .body(testLesson.toCreateRequest())
        .when()
        .post("/api/lesson/{courseId}", courseId)
        .then()
        .extract()
        .as(LessonDTO.class);
  }

  public static CourseDTO getCourse(String courseId) {
    return given().when().get("/api/course/{id}", courseId).then().extract().as(CourseDTO.class);
  }

  public static CourseDetailsDTO getCourseDetails(String token, String courseId) {
    return given()
        .header("Authorization", "Bearer " + token)
        .when()
        .get("/api/course/details/{id}", courseId)
        .then()
        .extract()
        .as(CourseDetailsDTO.class);
  }

  public static LessonDTO getLessonById(String token, String lessonId) {
    return given()
        .header("Authorization", "Bearer " + token)
        .when()
        .get("/api/lesson/{id}", lessonId)
        .then()
        .extract()
        .as(LessonDTO.class);
  }
}
