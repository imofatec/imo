package com.imo.backend.e2e.catalog;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

import com.imo.backend.contexts.catalog.course.http.dtos.CategoryDTO;
import com.imo.backend.contexts.catalog.course.http.dtos.CourseDTO;
import com.imo.backend.contexts.catalog.course.http.dtos.CourseDetailsDTO;
import com.imo.backend.contexts.catalog.course.http.dtos.UpdateCourseByIdRequest;
import com.imo.backend.contexts.catalog.course.value_objects.Categories;
import com.imo.backend.e2e.BaseE2ETest;
import com.imo.backend.e2e.catalog.helpers.CatalogSkillTestHelper;
import com.imo.backend.e2e.catalog.helpers.CatalogTestHelper;
import com.imo.backend.e2e.catalog.helpers.CatalogTestHelper.TestCourse;
import com.imo.backend.e2e.identity.helpers.IdentityTestHelper;
import com.imo.backend.e2e.identity.helpers.IdentityTestHelper.TestUser;
import io.restassured.http.ContentType;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class CourseE2ETest extends BaseE2ETest {

  @Test
  @DisplayName("happy path (POST /api/course): retorna 201 quando cria curso com aulas")
  void shouldCreateCourse() {
    String token = IdentityTestHelper.registerAndLogin(TestUser.defaultUser());
    TestCourse course = TestCourse.defaultCourse();

    CourseDetailsDTO created =
        given()
            .header("Authorization", "Bearer " + token)
            .contentType(ContentType.JSON)
            .body(course.toCreateRequest())
            .when()
            .post("/api/course")
            .then()
            .statusCode(HttpStatus.CREATED.value())
            .contentType(ContentType.JSON)
            .extract()
            .as(CourseDetailsDTO.class);

    assertNotNull(created.course().id());
    assertEquals(course.name(), created.course().name().name());
    assertEquals(course.category().getValue(), created.course().category().name());
    assertEquals(course.level(), created.course().level().name());
    assertEquals(course.description(), created.course().description());
    assertTrue(created.course().isActive());
    assertEquals(course.skillIds(), created.course().skillIds());
    assertEquals(1, created.lessons().size());
  }

  @Test
  @DisplayName("exception (POST /api/course): retorna 401 quando não autenticado")
  void shouldReturn401WhenNotAuthenticated() {
    TestCourse course = TestCourse.defaultCourse();

    given()
        .contentType(ContentType.JSON)
        .body(course.toCreateRequest())
        .when()
        .post("/api/course")
        .then()
        .statusCode(HttpStatus.UNAUTHORIZED.value())
        .body("error", equalTo("UNAUTHORIZED"));
  }

  @Test
  @DisplayName(
      "exception (POST /api/course): retorna 403 quando usuário autenticado não confirmou a conta")
  void shouldReturn403WhenUserIsNotConfirmed() {
    TestUser user = TestUser.defaultUser();
    IdentityTestHelper.registerUser(user.toCreateRequest());
    String token = IdentityTestHelper.login(user.toLoginRequest());
    TestCourse course = TestCourse.defaultCourse();

    given()
        .header("Authorization", "Bearer " + token)
        .contentType(ContentType.JSON)
        .body(course.toCreateRequest())
        .when()
        .post("/api/course")
        .then()
        .statusCode(HttpStatus.FORBIDDEN.value())
        .contentType(ContentType.JSON)
        .body("error", equalTo("FORBIDDEN"));
  }

  @Test
  @DisplayName(
      "exception (POST /api/course): retorna 409 quando usuário ja cadastrou curso com mesmo slug")
  void shouldReturn409WhenCourseSlugAlreadyExistsForUser() {
    String token = IdentityTestHelper.registerAndLogin(TestUser.defaultUser());
    TestCourse course = TestCourse.defaultCourse();

    given()
        .header("Authorization", "Bearer " + token)
        .contentType(ContentType.JSON)
        .body(course.toCreateRequest())
        .when()
        .post("/api/course");

    given()
        .header("Authorization", "Bearer " + token)
        .contentType(ContentType.JSON)
        .body(course.toCreateRequest())
        .when()
        .post("/api/course")
        .then()
        .statusCode(HttpStatus.CONFLICT.value())
        .contentType(ContentType.JSON)
        .body("error", equalTo("CONFLICT"));
  }

  @Test
  @DisplayName("happy path (GET /api/course/{id}): retorna 200 quando busca curso publico por id")
  void shouldGetCourseById() {
    String token = IdentityTestHelper.registerAndLogin(TestUser.defaultUser());
    CourseDetailsDTO createdDetails =
        CatalogTestHelper.createCourse(token, TestCourse.defaultCourse());

    CourseDTO course =
        given()
            .when()
            .get("/api/course/{id}", createdDetails.course().id())
            .then()
            .statusCode(HttpStatus.OK.value())
            .contentType(ContentType.JSON)
            .extract()
            .as(CourseDTO.class);

    assertEquals(createdDetails.course().id(), course.id());
    assertEquals(createdDetails.course().name().name(), course.name().name());
    assertEquals(createdDetails.course().description(), course.description());
    assertEquals(createdDetails.course().skillIds(), course.skillIds());
  }

  @Test
  @DisplayName("exception (GET /api/course/{id}): retorna 404 quando curso está inativo")
  void shouldReturn404WhenCourseIsInactive() {
    String token = IdentityTestHelper.registerAndLogin(TestUser.defaultUser());
    CourseDTO inactiveCourse =
        CatalogTestHelper.createInactiveCourse(token, TestCourse.defaultCourse());

    given()
        .when()
        .get("/api/course/{id}", inactiveCourse.id())
        .then()
        .statusCode(HttpStatus.NOT_FOUND.value())
        .contentType(ContentType.JSON)
        .body("error", equalTo("NOT_FOUND"));
  }

  @Test
  @DisplayName("happy path (GET /api/course/inactive/{id}): retorna 200 quando busca curso inativo")
  void shouldGetInactiveCourseById() {
    String token = IdentityTestHelper.registerAndLogin(TestUser.defaultUser());
    CourseDTO inactiveCourse =
        CatalogTestHelper.createInactiveCourse(token, TestCourse.defaultCourse());

    CourseDTO course =
        given()
            .when()
            .get("/api/course/inactive/{id}", inactiveCourse.id())
            .then()
            .statusCode(HttpStatus.OK.value())
            .contentType(ContentType.JSON)
            .extract()
            .as(CourseDTO.class);

    assertEquals(inactiveCourse.id(), course.id());
    assertFalse(course.isActive());
  }

  @Test
  @DisplayName("exception (GET /api/course/{id}): retorna 400 quando id é invalido")
  void shouldReturn400WhenCourseIdIsInvalid() {
    given()
        .when()
        .get("/api/course/{id}", "invalid-id")
        .then()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .contentType(ContentType.JSON)
        .body("error", equalTo("BAD_REQUEST"));
  }

  @Test
  @DisplayName("happy path (GET /api/course/categories): retorna 200 com lista de categorias")
  void shouldGetCategories() {
    List<CategoryDTO> categories =
        given()
            .when()
            .get("/api/course/categories")
            .then()
            .statusCode(HttpStatus.OK.value())
            .contentType(ContentType.JSON)
            .extract()
            .jsonPath()
            .getList(".", CategoryDTO.class);

    assertFalse(categories.isEmpty());
  }

  @Test
  @DisplayName("happy path (GET /api/course/search): retorna 200 com lista de cursos paginada")
  void shouldSearchCourses() {
    String token = IdentityTestHelper.registerAndLogin(TestUser.defaultUser());
    TestCourse course = TestCourse.defaultCourse();
    CatalogTestHelper.createCourse(token, course);

    given()
        .queryParam("name", course.name())
        .queryParam("page", 0)
        .queryParam("size", 10)
        .when()
        .get("/api/course/search")
        .then()
        .statusCode(HttpStatus.OK.value())
        .contentType(ContentType.JSON)
        .body("pagination.currentPage", equalTo(0))
        .body("pagination.remainingPages", equalTo(0));
  }

  @Test
  @DisplayName(
      "happy path (GET /api/course/search): retorna vazio quando curso está inativo por padrão")
  void shouldNotSearchInactiveCoursesByDefault() {
    String token = IdentityTestHelper.registerAndLogin(TestUser.defaultUser());
    TestCourse course = TestCourse.defaultCourse();
    CatalogTestHelper.createInactiveCourse(token, course);

    List<CourseDTO> courses =
        given()
            .queryParam("name", course.name())
            .queryParam("page", 0)
            .queryParam("size", 10)
            .when()
            .get("/api/course/search")
            .then()
            .statusCode(HttpStatus.OK.value())
            .contentType(ContentType.JSON)
            .extract()
            .jsonPath()
            .getList("items", CourseDTO.class);

    assertTrue(courses.isEmpty());
  }

  @Test
  @DisplayName("happy path (GET /api/course/search): retorna cursos inativos quando active=false")
  void shouldSearchInactiveCoursesWhenActiveIsFalse() {
    String token = IdentityTestHelper.registerAndLogin(TestUser.defaultUser());
    TestCourse course = TestCourse.defaultCourse();
    CourseDTO inactiveCourse = CatalogTestHelper.createInactiveCourse(token, course);

    List<CourseDTO> courses =
        given()
            .queryParam("name", course.name())
            .queryParam("active", false)
            .queryParam("page", 0)
            .queryParam("size", 10)
            .when()
            .get("/api/course/search")
            .then()
            .statusCode(HttpStatus.OK.value())
            .contentType(ContentType.JSON)
            .extract()
            .jsonPath()
            .getList("items", CourseDTO.class);

    assertEquals(1, courses.size());
    assertEquals(inactiveCourse.id(), courses.getFirst().id());
    assertFalse(courses.getFirst().isActive());
  }

  @Test
  @DisplayName(
      "happy path (GET /api/course/search/details): retorna 200 com detalhes dos cursos paginada")
  void shouldSearchCourseDetails() {
    String token = IdentityTestHelper.registerAndLogin(TestUser.defaultUser());
    TestCourse course = TestCourse.defaultCourse();
    CatalogTestHelper.createCourse(token, course);

    given()
        .header("Authorization", "Bearer " + token)
        .queryParam("name", course.name())
        .queryParam("page", 0)
        .queryParam("size", 10)
        .when()
        .get("/api/course/search/details")
        .then()
        .statusCode(HttpStatus.OK.value())
        .contentType(ContentType.JSON)
        .body("pagination.currentPage", equalTo(0))
        .body("pagination.remainingPages", equalTo(0));
  }

  @Test
  @DisplayName(
      "happy path (GET /api/course/search/details): retorna vazio por padrão quando curso está inativo")
  void shouldNotSearchInactiveCourseDetailsByDefault() {
    String token = IdentityTestHelper.registerAndLogin(TestUser.defaultUser());
    TestCourse course = TestCourse.defaultCourse();
    CatalogTestHelper.createInactiveCourse(token, course);

    List<CourseDetailsDTO> details =
        given()
            .header("Authorization", "Bearer " + token)
            .queryParam("name", course.name())
            .queryParam("page", 0)
            .queryParam("size", 10)
            .when()
            .get("/api/course/search/details")
            .then()
            .statusCode(HttpStatus.OK.value())
            .contentType(ContentType.JSON)
            .extract()
            .jsonPath()
            .getList("items", CourseDetailsDTO.class);

    assertTrue(details.isEmpty());
  }

  @Test
  @DisplayName(
      "happy path (GET /api/course/search/details): retorna cursos inativos quando active=false")
  void shouldSearchInactiveCourseDetailsWhenActiveIsFalse() {
    String token = IdentityTestHelper.registerAndLogin(TestUser.defaultUser());
    TestCourse course = TestCourse.defaultCourse();
    CourseDTO inactiveCourse = CatalogTestHelper.createInactiveCourse(token, course);

    List<CourseDetailsDTO> details =
        given()
            .header("Authorization", "Bearer " + token)
            .queryParam("name", course.name())
            .queryParam("active", false)
            .queryParam("page", 0)
            .queryParam("size", 10)
            .when()
            .get("/api/course/search/details")
            .then()
            .statusCode(HttpStatus.OK.value())
            .contentType(ContentType.JSON)
            .extract()
            .jsonPath()
            .getList("items", CourseDetailsDTO.class);

    assertEquals(1, details.size());
    assertEquals(inactiveCourse.id(), details.getFirst().course().id());
    assertFalse(details.getFirst().course().isActive());
  }

  @Test
  @DisplayName("exception (GET /api/course/search/details): retorna 401 quando não autenticado")
  void shouldReturn401WhenSearchingCourseDetailsNotAuthenticated() {
    given()
        .queryParam("name", "curso")
        .queryParam("page", 0)
        .queryParam("size", 10)
        .when()
        .get("/api/course/search/details")
        .then()
        .statusCode(HttpStatus.UNAUTHORIZED.value())
        .body("error", equalTo("UNAUTHORIZED"));
  }

  @Test
  @DisplayName(
      "happy path (GET /api/course/details/{id}): retorna 200 quando busca detalhes pelo id")
  void shouldGetCourseDetailsById() {
    String token = IdentityTestHelper.registerAndLogin(TestUser.defaultUser());
    CourseDetailsDTO createdDetails =
        CatalogTestHelper.createCourse(token, TestCourse.defaultCourse());

    given()
        .header("Authorization", "Bearer " + token)
        .when()
        .get("/api/course/details/" + createdDetails.course().id())
        .then()
        .statusCode(HttpStatus.OK.value())
        .contentType(ContentType.JSON)
        .extract()
        .as(CourseDetailsDTO.class);
  }

  @Test
  @DisplayName("exception (GET /api/course/details/{id}): retorna 404 quando curso está inativo")
  void shouldReturn404WhenCourseDetailsAreInactive() {
    String token = IdentityTestHelper.registerAndLogin(TestUser.defaultUser());
    CourseDTO inactiveCourse =
        CatalogTestHelper.createInactiveCourse(token, TestCourse.defaultCourse());

    given()
        .header("Authorization", "Bearer " + token)
        .when()
        .get("/api/course/details/" + inactiveCourse.id())
        .then()
        .statusCode(HttpStatus.NOT_FOUND.value())
        .contentType(ContentType.JSON)
        .body("error", equalTo("NOT_FOUND"));
  }

  @Test
  @DisplayName(
      "happy path (GET /api/course/details/inactive/{id}): retorna 200 quando busca detalhes de curso inativo")
  void shouldGetInactiveCourseDetailsById() {
    String token = IdentityTestHelper.registerAndLogin(TestUser.defaultUser());
    CourseDTO inactiveCourse =
        CatalogTestHelper.createInactiveCourse(token, TestCourse.defaultCourse());

    CourseDetailsDTO courseDetails =
        given()
            .when()
            .get("/api/course/details/inactive/{id}", inactiveCourse.id())
            .then()
            .statusCode(HttpStatus.OK.value())
            .contentType(ContentType.JSON)
            .extract()
            .as(CourseDetailsDTO.class);

    assertEquals(inactiveCourse.id(), courseDetails.course().id());
    assertFalse(courseDetails.course().isActive());
    assertFalse(courseDetails.lessons().isEmpty());
  }

  @Test
  @DisplayName("exception (GET /api/course/details/{id}): retorna 404 quando detalhe não existe")
  void shouldReturn404WhenNonExistentDetails() {
    String token = IdentityTestHelper.registerAndLogin(TestUser.defaultUser());
    String nonExistentId = "000000000000000000000001";

    given()
        .header("Authorization", "Bearer " + token)
        .when()
        .get("/api/course/details/" + nonExistentId)
        .then()
        .statusCode(HttpStatus.NOT_FOUND.value())
        .contentType(ContentType.JSON)
        .body("error", equalTo("NOT_FOUND"));
  }

  @Test
  @DisplayName(
      "happy path (PUT /api/course/{id}): retorna 200 quando atualiza curso do próprio usuário")
  void shouldUpdateCourse() {
    String token = IdentityTestHelper.registerAndLogin(TestUser.defaultUser());
    CourseDetailsDTO createdDetails =
        CatalogTestHelper.createCourse(token, TestCourse.defaultCourse());

    String updatedName = "Curso de Java Atualizado";
    String updatedDescription = "Descrição atualizada do curso de Java";
    List<String> updatedSkillIds =
        CatalogSkillTestHelper.getSkillIdsForCategory(Categories.DATA, 2);

    UpdateCourseByIdRequest updateRequest =
        new UpdateCourseByIdRequest(
            updatedName, Categories.DATA, "Intermediário", updatedDescription, updatedSkillIds);

    CourseDTO updated =
        given()
            .header("Authorization", "Bearer " + token)
            .contentType(ContentType.JSON)
            .body(updateRequest)
            .when()
            .put("/api/course/{id}", createdDetails.course().id())
            .then()
            .statusCode(HttpStatus.OK.value())
            .contentType(ContentType.JSON)
            .extract()
            .as(CourseDTO.class);

    assertEquals(createdDetails.course().id(), updated.id());
    assertEquals(updatedName, updated.name().name());
    assertEquals(updatedDescription, updated.description());
    assertEquals(Categories.DATA.getValue(), updated.category().name());
    assertEquals(updatedSkillIds, updated.skillIds());
  }

  @Test
  @DisplayName("exception (PUT /api/course/{id}): retorna 401 quando não autenticado")
  void shouldReturn401WhenUpdatingCourseNotAuthenticated() {
    String token = IdentityTestHelper.registerAndLogin(TestUser.defaultUser());
    CourseDetailsDTO createdDetails =
        CatalogTestHelper.createCourse(token, TestCourse.defaultCourse());

    UpdateCourseByIdRequest updateRequest =
        new UpdateCourseByIdRequest("Curso de Java Atualizado", null, null, null, null);

    given()
        .contentType(ContentType.JSON)
        .body(updateRequest)
        .when()
        .put("/api/course/{id}", createdDetails.course().id())
        .then()
        .statusCode(HttpStatus.UNAUTHORIZED.value())
        .body("error", equalTo("UNAUTHORIZED"));
  }

  @Test
  @DisplayName("exception (PUT /api/course/{id}): retorna 403 quando outro usuário tenta atualizar")
  void shouldReturn403WhenUpdatingCourseOfAnotherUser() {
    String tokenA = IdentityTestHelper.registerAndLogin(TestUser.defaultUser());
    CourseDetailsDTO createdDetails =
        CatalogTestHelper.createCourse(tokenA, TestCourse.defaultCourse());

    String tokenB =
        IdentityTestHelper.registerAndLogin(
            new TestUser("Outro Usuário", "outro@email.com", "Teste123"));

    UpdateCourseByIdRequest updateRequest =
        new UpdateCourseByIdRequest("Curso Hackeado", null, null, null, null);

    given()
        .header("Authorization", "Bearer " + tokenB)
        .contentType(ContentType.JSON)
        .body(updateRequest)
        .when()
        .put("/api/course/{id}", createdDetails.course().id())
        .then()
        .statusCode(HttpStatus.FORBIDDEN.value())
        .contentType(ContentType.JSON)
        .body("error", equalTo("FORBIDDEN"));
  }

  @Test
  @DisplayName("exception (PUT /api/course/{id}): retorna 404 quando curso não existe")
  void shouldReturn404WhenUpdatingNonExistentCourse() {
    String token = IdentityTestHelper.registerAndLogin(TestUser.defaultUser());

    UpdateCourseByIdRequest updateRequest =
        new UpdateCourseByIdRequest("Curso Inexistente", null, null, null, null);

    String nonExistentId = "000000000000000000000001";

    given()
        .header("Authorization", "Bearer " + token)
        .contentType(ContentType.JSON)
        .body(updateRequest)
        .when()
        .put("/api/course/{id}", nonExistentId)
        .then()
        .statusCode(HttpStatus.NOT_FOUND.value())
        .contentType(ContentType.JSON)
        .body("error", equalTo("NOT_FOUND"));
  }

  @Test
  @DisplayName("happy path (PATCH /api/course/{id}): retorna 200 quando faz toggle do status")
  void shouldToggleCourseStatus() {
    String token = IdentityTestHelper.registerAndLogin(TestUser.defaultUser());
    CourseDetailsDTO createdDetails =
        CatalogTestHelper.createCourse(token, TestCourse.defaultCourse());
    assertTrue(createdDetails.course().isActive());

    CourseDTO toggled =
        given()
            .header("Authorization", "Bearer " + token)
            .when()
            .patch("/api/course/{id}", createdDetails.course().id())
            .then()
            .statusCode(HttpStatus.OK.value())
            .contentType(ContentType.JSON)
            .extract()
            .as(CourseDTO.class);

    assertEquals(createdDetails.course().id(), toggled.id());
    assertFalse(toggled.isActive());
  }

  @Test
  @DisplayName(
      "exception (PATCH /api/course/{id}): retorna 403 quando outro usuário tenta alterar status")
  void shouldReturn403WhenTogglingStatusOfAnotherUsersCourse() {
    String tokenA = IdentityTestHelper.registerAndLogin(TestUser.defaultUser());
    CourseDetailsDTO createdDetails =
        CatalogTestHelper.createCourse(tokenA, TestCourse.defaultCourse());

    String tokenB =
        IdentityTestHelper.registerAndLogin(
            new TestUser("Outro Usuário", "outro2@email.com", "Teste123"));

    given()
        .header("Authorization", "Bearer " + tokenB)
        .when()
        .patch("/api/course/{id}", createdDetails.course().id())
        .then()
        .statusCode(HttpStatus.FORBIDDEN.value())
        .contentType(ContentType.JSON)
        .body("error", equalTo("FORBIDDEN"));
  }
}
