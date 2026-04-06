package com.imo.backend.e2e;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

class HealthCheckE2ETest extends BaseE2ETest {
  @Test
  @DisplayName("happy path (GET /api/health-check): retorna status 200 com mensagem OK")
  void shouldReturnOk() {
    given()
        .when()
        .get("/api/health-check")
        .then()
        .statusCode(HttpStatus.OK.value())
        .contentType(ContentType.JSON)
        .body("message", equalTo("OK"));
  }
}
