package com.imo.backend.e2e.catalog;

import com.imo.backend.contexts.catalog.course.http.dtos.CourseDTO;
import com.imo.backend.contexts.catalog.course.http.dtos.CourseDetailsDTO;
import com.imo.backend.contexts.catalog.lesson.http.dtos.LessonDTO;
import com.imo.backend.contexts.catalog.lesson.http.dtos.UpdateLessonRequest;
import com.imo.backend.e2e.BaseE2ETest;
import com.imo.backend.e2e.catalog.helpers.CatalogTestHelper;
import com.imo.backend.e2e.catalog.helpers.CatalogTestHelper.TestCourse;
import com.imo.backend.e2e.catalog.helpers.CatalogTestHelper.TestLesson;
import com.imo.backend.e2e.identity.helpers.IdentityTestHelper;
import com.imo.backend.e2e.identity.helpers.IdentityTestHelper.TestUser;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

class LessonE2ETest extends BaseE2ETest {

  @Test
  @DisplayName("happy path (POST /api/lesson/{courseId}): retorna 201 quando cria aula e incrementa lessonsCount")
  void shouldCreateLesson() {
    String token = IdentityTestHelper.registerAndLogin(TestUser.defaultUser());
    CourseDetailsDTO courseDetails = CatalogTestHelper.createCourse(
        token,
        TestCourse.defaultCourse()
    );
    int lessonsCountBefore = courseDetails.course().lessonsCount();
    TestLesson lesson = new TestLesson(
        "Aula de Variáveis",
        "Aprenda a declarar variáveis em Java",
        "IIFvUENepXk"
    );

    LessonDTO created = given()
        .header("Authorization", "Bearer " + token)
        .contentType(ContentType.JSON)
        .body(lesson.toCreateRequest())
        .when()
        .post("/api/lesson/{courseId}", courseDetails.course().id())
        .then()
        .statusCode(HttpStatus.CREATED.value())
        .contentType(ContentType.JSON)
        .extract()
        .as(LessonDTO.class);

    assertNotNull(created.id());
    assertEquals(lesson.title(), created.title());
    assertEquals(lesson.youtubeLink(), created.youtubeLink());
    assertEquals(courseDetails.course().id(), created.courseId());

    CourseDTO updatedCourse = CatalogTestHelper.getCourse(courseDetails.course().id());
    assertEquals(lessonsCountBefore + 1, updatedCourse.lessonsCount());
  }

  @Test
  @DisplayName("exception (POST /api/lesson/{courseId}): retorna 401 quando não autenticado")
  void shouldReturn401WhenCreatingLessonNotAuthenticated() {
    String token = IdentityTestHelper.registerAndLogin(TestUser.defaultUser());
    CourseDetailsDTO courseDetails = CatalogTestHelper.createCourse(
        token,
        TestCourse.defaultCourse()
    );
    TestLesson lesson = TestLesson.defaultLesson();

    given()
        .contentType(ContentType.JSON)
        .body(lesson.toCreateRequest())
        .when()
        .post("/api/lesson/{courseId}", courseDetails.course().id())
        .then()
        .statusCode(HttpStatus.UNAUTHORIZED.value())
        .body("error", equalTo("UNAUTHORIZED"));
  }

  @Test
  @DisplayName("exception (POST /api/lesson/{courseId}): retorna 403 quando outro usuário tenta criar aula no curso de outro")
  void shouldReturn403WhenCreatingLessonInAnotherUsersCourse() {
    String tokenA = IdentityTestHelper.registerAndLogin(TestUser.defaultUser());
    CourseDetailsDTO courseDetails = CatalogTestHelper.createCourse(
        tokenA,
        TestCourse.defaultCourse()
    );

    String tokenB = IdentityTestHelper.registerAndLogin(new TestUser(
        "Outro Usuário",
        "outro-lesson@email.com",
        "Teste123"
    ));
    TestLesson lesson = TestLesson.defaultLesson();

    given()
        .header("Authorization", "Bearer " + tokenB)
        .contentType(ContentType.JSON)
        .body(lesson.toCreateRequest())
        .when()
        .post("/api/lesson/{courseId}", courseDetails.course().id())
        .then()
        .statusCode(HttpStatus.FORBIDDEN.value())
        .contentType(ContentType.JSON)
        .body("error", equalTo("FORBIDDEN"));
  }

  @Test
  @DisplayName("exception (POST /api/lesson/{courseId}): retorna 400 quando courseId é inválido")
  void shouldReturn400WhenCourseIdIsInvalid() {
    String token = IdentityTestHelper.registerAndLogin(TestUser.defaultUser());
    TestLesson lesson = TestLesson.defaultLesson();

    given()
        .header("Authorization", "Bearer " + token)
        .contentType(ContentType.JSON)
        .body(lesson.toCreateRequest())
        .when()
        .post("/api/lesson/{courseId}", "invalid-id")
        .then()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .contentType(ContentType.JSON)
        .body("error", equalTo("BAD_REQUEST"));
  }

  @Test
  @DisplayName("happy path (PUT /api/lesson/{id}): retorna 200 quando atualiza aula e mantém lessonsCount")
  void shouldUpdateLesson() {
    String token = IdentityTestHelper.registerAndLogin(TestUser.defaultUser());
    CourseDetailsDTO courseDetails = CatalogTestHelper.createCourse(
        token,
        TestCourse.defaultCourse()
    );
    TestLesson lesson = new TestLesson(
        "Aula Extra do Curso",
        "Aula adicional para teste",
        "CCTbRbEPoB4"
    );
    LessonDTO created = CatalogTestHelper.createLesson(token, courseDetails.course().id(), lesson);
    int lessonsCountBefore = courseDetails.course().lessonsCount() + 1;

    String updatedName = "Aula Atualizada com Novo Título";
    String updatedDescription = "Descrição atualizada da aula";

    UpdateLessonRequest updateRequest = new UpdateLessonRequest(
        updatedName,
        updatedDescription,
        null
    );

    LessonDTO updated = given()
        .header("Authorization", "Bearer " + token)
        .contentType(ContentType.JSON)
        .body(updateRequest)
        .when()
        .put("/api/lesson/{id}", created.id())
        .then()
        .statusCode(HttpStatus.OK.value())
        .contentType(ContentType.JSON)
        .extract()
        .as(LessonDTO.class);

    assertEquals(created.id(), updated.id());
    assertEquals(updatedName, updated.title());
    assertEquals(updatedDescription, updated.description());
    assertEquals(created.youtubeLink(), updated.youtubeLink());

    CourseDTO updatedCourse = CatalogTestHelper.getCourse(courseDetails.course().id());
    assertEquals(lessonsCountBefore, updatedCourse.lessonsCount());
  }

  @Test
  @DisplayName("exception (PUT /api/lesson/{id}): retorna 401 quando não autenticado")
  void shouldReturn401WhenUpdatingLessonNotAuthenticated() {
    String token = IdentityTestHelper.registerAndLogin(TestUser.defaultUser());
    CourseDetailsDTO courseDetails = CatalogTestHelper.createCourse(
        token,
        TestCourse.defaultCourse()
    );
    TestLesson lesson = new TestLesson(
        "Aula Extra do Curso",
        "Aula adicional para teste",
        "iPKEcdIbUEE"
    );
    LessonDTO created = CatalogTestHelper.createLesson(token, courseDetails.course().id(), lesson);

    UpdateLessonRequest updateRequest = new UpdateLessonRequest("Título Atualizado", null, null);

    given()
        .contentType(ContentType.JSON)
        .body(updateRequest)
        .when()
        .put("/api/lesson/{id}", created.id())
        .then()
        .statusCode(HttpStatus.UNAUTHORIZED.value())
        .body("error", equalTo("UNAUTHORIZED"));
  }

  @Test
  @DisplayName("exception (PUT /api/lesson/{id}): retorna 403 quando outro usuário tenta atualizar aula de outro")
  void shouldReturn403WhenUpdatingLessonOfAnotherUsersCourse() {
    String tokenA = IdentityTestHelper.registerAndLogin(TestUser.defaultUser());
    CourseDetailsDTO createdCourseDetails = CatalogTestHelper.createCourse(
        tokenA,
        TestCourse.defaultCourse()
    );
    TestLesson lesson = new TestLesson(
        "Aula Extra do Curso",
        "Aula adicional para teste",
        "pkP4lKEonuk"
    );
    LessonDTO created = CatalogTestHelper.createLesson(
        tokenA,
        createdCourseDetails.course().id(),
        lesson
    );

    String tokenB = IdentityTestHelper.registerAndLogin(new TestUser(
        "Outro Usuário",
        "outro-update@email.com",
        "Teste123"
    ));

    UpdateLessonRequest updateRequest = new UpdateLessonRequest("Aula Hackeada", null, null);

    given()
        .header("Authorization", "Bearer " + tokenB)
        .contentType(ContentType.JSON)
        .body(updateRequest)
        .when()
        .put("/api/lesson/{id}", created.id())
        .then()
        .statusCode(HttpStatus.FORBIDDEN.value())
        .contentType(ContentType.JSON)
        .body("error", equalTo("FORBIDDEN"));

    CourseDetailsDTO courseDetails = CatalogTestHelper.getCourseDetails(
        tokenA,
        createdCourseDetails.course().id()
    );
    assertTrue(courseDetails
        .lessons()
        .stream()
        .noneMatch(l -> "Aula Hackeada".equals(l.getTitle())));
  }

  @Test
  @DisplayName("happy path (DELETE /api/lesson/{id}): retorna 200 quando deleta aula e decrementa lessonsCount")
  void shouldDeleteLesson() {
    String token = IdentityTestHelper.registerAndLogin(TestUser.defaultUser());
    CourseDetailsDTO createdCourseDetails = CatalogTestHelper.createCourse(
        token,
        TestCourse.defaultCourse()
    );
    TestLesson lesson = new TestLesson(
        "Aula Para Deletar",
        "Aula que sera removida",
        "ml7iqyUwhOg"
    );
    LessonDTO created = CatalogTestHelper.createLesson(
        token,
        createdCourseDetails.course().id(),
        lesson
    );
    int lessonsCountAfterCreate = createdCourseDetails.course().lessonsCount() + 1;

    LessonDTO deleted = given()
        .header("Authorization", "Bearer " + token)
        .when()
        .delete("/api/lesson/{id}", created.id())
        .then()
        .statusCode(HttpStatus.OK.value())
        .contentType(ContentType.JSON)
        .extract()
        .as(LessonDTO.class);

    assertEquals(created.id(), deleted.id());

    CourseDTO updatedCourse = CatalogTestHelper.getCourse(createdCourseDetails.course().id());
    assertEquals(lessonsCountAfterCreate - 1, updatedCourse.lessonsCount());

    CourseDetailsDTO courseDetails = CatalogTestHelper.getCourseDetails(
        token,
        createdCourseDetails.course().id()
    );
    assertTrue(courseDetails.lessons().stream().noneMatch(l -> l.getId().equals(created.id())));
  }

  @Test
  @DisplayName("exception (DELETE /api/lesson/{id}): retorna 403 quando outro usuário tenta deletar aula de outro")
  void shouldReturn403WhenDeletingLessonOfAnotherUsersCourse() {
    String tokenA = IdentityTestHelper.registerAndLogin(TestUser.defaultUser());
    CourseDetailsDTO createdCourseDetails = CatalogTestHelper.createCourse(
        tokenA,
        TestCourse.defaultCourse()
    );
    TestLesson lesson = new TestLesson(
        "Aula Protegida",
        "Aula que nao pode ser deletada por outros",
        "674H5DJ7ECQ"
    );
    LessonDTO created = CatalogTestHelper.createLesson(
        tokenA,
        createdCourseDetails.course().id(),
        lesson
    );
    int lessonsCountBefore = createdCourseDetails.course().lessonsCount() + 1;

    String tokenB = IdentityTestHelper.registerAndLogin(new TestUser(
        "Outro Usuário",
        "outro-delete@email.com",
        "Teste123"
    ));

    given()
        .header("Authorization", "Bearer " + tokenB)
        .when()
        .delete("/api/lesson/{id}", created.id())
        .then()
        .statusCode(HttpStatus.FORBIDDEN.value())
        .contentType(ContentType.JSON)
        .body("error", equalTo("FORBIDDEN"));

    CourseDTO courseAfter = CatalogTestHelper.getCourse(createdCourseDetails.course().id());
    assertEquals(lessonsCountBefore, courseAfter.lessonsCount());

    CourseDetailsDTO courseDetails = CatalogTestHelper.getCourseDetails(
        tokenA,
        createdCourseDetails.course().id()
    );
    assertTrue(courseDetails.lessons().stream().anyMatch(l -> l.getId().equals(created.id())));
  }

  @Test
  @DisplayName("happy path (GET /api/lesson/search): retorna 200 com aulas paginadas")
  void shouldSearchLessons() {
    String token = IdentityTestHelper.registerAndLogin(TestUser.defaultUser());
    CourseDetailsDTO courseDetails = CatalogTestHelper.createCourse(
        token,
        TestCourse.defaultCourse()
    );
    TestLesson lesson = new TestLesson("Aula Buscável", "Aula para testar busca", "Ohk73dP1voE");
    CatalogTestHelper.createLesson(token, courseDetails.course().id(), lesson);

    given()
        .header("Authorization", "Bearer " + token)
        .queryParam("courseName", courseDetails.course().name().name())
        .queryParam("page", 0)
        .queryParam("size", 10)
        .when()
        .get("/api/lesson/search")
        .then()
        .statusCode(HttpStatus.OK.value())
        .contentType(ContentType.JSON);
  }

  @Test
  @DisplayName("happy path (GET /api/lesson/{id}): retorna 200 quando busca pelo id")
  void shouldGetLessonById() {
    String token = IdentityTestHelper.registerAndLogin(TestUser.defaultUser());
    CourseDetailsDTO courseDetails = CatalogTestHelper.createCourse(
        token,
        TestCourse.defaultCourse()
    );
    LessonDTO newLesson = CatalogTestHelper.createLesson(
        token,
        courseDetails.course().id(),
        new TestLesson("Aula Buscável", "Aula para testar busca", "Ohk73dP1voE")
    );

    given()
        .header("Authorization", "Bearer " + token)
        .when()
        .get("/api/lesson/" + newLesson.id())
        .then()
        .statusCode(HttpStatus.OK.value())
        .contentType(ContentType.JSON);
  }

  @Test
  @DisplayName("exception (GET /api/lesson/{id}): retorna 404 pra aula que não existe")
  void shouldReturn404WhenNonExistentLesson() {
    String token = IdentityTestHelper.registerAndLogin(TestUser.defaultUser());
    String nonExistentId = "000000000000000000000001";

    given()
        .header("Authorization", "Bearer " + token)
        .when()
        .get("/api/lesson/" + nonExistentId)
        .then()
        .statusCode(HttpStatus.NOT_FOUND.value())
        .contentType(ContentType.JSON)
        .body("error", equalTo("NOT_FOUND"));
  }
}
