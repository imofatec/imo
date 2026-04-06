package com.imo.backend.e2e.certification;

import com.imo.backend.e2e.BaseE2ETest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

class CertificateE2ETest extends BaseE2ETest {

  @Test
  @DisplayName("exception (GET /api/certificate/details/{id}): retorna 404 quando certificado não existe")
  void shouldReturn404WhenCertificateNotFound() {
    given()
        .when()
        .get("/api/certificate/details/{id}", "000000000000000000000001")
        .then()
        .statusCode(HttpStatus.NOT_FOUND.value())
        .contentType(ContentType.JSON)
        .body("error", equalTo("NOT_FOUND"));
  }

  @Test
  @DisplayName("exception (GET /api/certificate/details/{id}): retorna 400 quando id é inválido")
  void shouldReturn400WhenCertificateIdIsInvalid() {
    given()
        .when()
        .get("/api/certificate/details/{id}", "invalid-id")
        .then()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .contentType(ContentType.JSON)
        .body("error", equalTo("BAD_REQUEST"));
  }
}
