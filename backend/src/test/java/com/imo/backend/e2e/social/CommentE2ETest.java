package com.imo.backend.e2e.social;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.imo.backend.contexts.catalog.course.http.dtos.CourseDetailsDTO;
import com.imo.backend.contexts.social.comment.http.dtos.CommentDTO;
import com.imo.backend.e2e.BaseE2ETest;
import com.imo.backend.e2e.catalog.helpers.CatalogTestHelper;
import com.imo.backend.e2e.catalog.helpers.CatalogTestHelper.TestCourse;
import com.imo.backend.e2e.identity.helpers.IdentityTestHelper;
import com.imo.backend.e2e.identity.helpers.IdentityTestHelper.TestUser;
import com.imo.backend.e2e.social.helpers.SocialTestHelper;
import com.imo.backend.e2e.social.helpers.SocialTestHelper.TestComment;
import io.restassured.http.ContentType;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class CommentE2ETest extends BaseE2ETest {

  @Test
  @DisplayName("happy path (POST /api/comment/{lessonId}): retorna 201 quando cria comentário")
  void shouldCreateComment() {
    String token = IdentityTestHelper.registerAndLogin(TestUser.defaultUser());
    CourseDetailsDTO course = CatalogTestHelper.createCourse(token, TestCourse.defaultCourse());
    String lessonId = course.lessons().getFirst().getId();

    TestComment comment = SocialTestHelper.TestComment.defaultComment();

    CommentDTO created =
        given()
            .header("Authorization", "Bearer " + token)
            .contentType(ContentType.JSON)
            .body(comment.toCreateRequest())
            .when()
            .post("/api/comment/{lessonId}", lessonId)
            .then()
            .statusCode(HttpStatus.CREATED.value())
            .contentType(ContentType.JSON)
            .extract()
            .as(CommentDTO.class);

    assertNotNull(created.id());
    assertEquals(comment.content(), created.content());
    assertEquals(lessonId, created.lessonId());
  }

  @Test
  @DisplayName("exception (POST /api/comment/{lessonId}): retorna 401 quando não autenticado")
  void shouldReturn401WhenCreatingCommentNotAuthenticated() {
    String token = IdentityTestHelper.registerAndLogin(TestUser.defaultUser());
    CourseDetailsDTO course = CatalogTestHelper.createCourse(token, TestCourse.defaultCourse());
    String lessonId = course.lessons().getFirst().getId();

    given()
        .contentType(ContentType.JSON)
        .body(TestComment.defaultComment().toCreateRequest())
        .when()
        .post("/api/comment/{lessonId}", lessonId)
        .then()
        .statusCode(HttpStatus.UNAUTHORIZED.value())
        .body("error", equalTo("UNAUTHORIZED"));
  }

  @Test
  @DisplayName("happy path (GET /api/comment/{lessonId}): retorna 200 com lista de comentários")
  void shouldGetCommentsByLessonId() {
    String token = IdentityTestHelper.registerAndLogin(TestUser.defaultUser());
    CourseDetailsDTO course = CatalogTestHelper.createCourse(token, TestCourse.defaultCourse());
    String lessonId = course.lessons().getFirst().getId();
    SocialTestHelper.createComment(token, lessonId, TestComment.defaultComment());

    List<CommentDTO> comments =
        given()
            .queryParam("page", 0)
            .queryParam("size", 10)
            .when()
            .get("/api/comment/{lessonId}", lessonId)
            .then()
            .statusCode(HttpStatus.OK.value())
            .contentType(ContentType.JSON)
            .extract()
            .jsonPath()
            .getList(".", CommentDTO.class);

    assertNotNull(comments);
  }
}
