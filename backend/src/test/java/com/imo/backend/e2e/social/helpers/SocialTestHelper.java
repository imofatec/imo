package com.imo.backend.e2e.social.helpers;

import static io.restassured.RestAssured.given;

import com.imo.backend.contexts.social.comment.http.dtos.CommentDTO;
import com.imo.backend.contexts.social.comment.http.dtos.CreateCommentRequest;
import io.restassured.http.ContentType;

public final class SocialTestHelper {
  public record TestComment(String content, String parentId) {
    public static TestComment defaultComment() {
      return new TestComment("Excelente aula", null);
    }

    public CreateCommentRequest toCreateRequest() {
      return new CreateCommentRequest(content, parentId);
    }
  }

  public static CommentDTO createComment(String token, String lessonId, TestComment testComment) {
    return given()
        .header("Authorization", "Bearer " + token)
        .contentType(ContentType.JSON)
        .body(testComment.toCreateRequest())
        .when()
        .post("/api/comment/{lessonId}", lessonId)
        .then()
        .extract()
        .as(CommentDTO.class);
  }
}
