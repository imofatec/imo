package com.imo.backend.e2e.notification;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import com.imo.backend.e2e.BaseE2ETest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class NotificationE2ETest extends BaseE2ETest {
  @Test
  @DisplayName(
      "exception (GET /api/notification/stream/{token}): retorna 401 quando token é inválido")
  void shouldReturn401WhenTokenIsInvalid() {
    given()
        .when()
        .get("/api/notification/stream/{token}", "invalid-token")
        .then()
        .statusCode(HttpStatus.UNAUTHORIZED.value())
        .contentType(ContentType.JSON)
        .body("error", equalTo("UNAUTHORIZED"))
        .body("message", equalTo("Token inválido ou expirado"));
  }
}
