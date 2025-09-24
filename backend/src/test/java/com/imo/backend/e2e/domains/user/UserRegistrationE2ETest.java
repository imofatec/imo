package com.imo.backend.e2e.domains.user;

import com.imo.backend.e2e.config.BaseE2ETest;
import com.imo.backend.e2e.testfactory.UserTestFactory;
import com.imo.backend.e2e.utils.JsonString;
import com.imo.backend.modules.user.actions.inputs.CreateUserInput;
import io.restassured.http.ContentType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@Slf4j
public class UserRegistrationE2ETest extends BaseE2ETest {

  private final JsonString jsonString = new JsonString();

  @Test
  @DisplayName("Cria um user via POST /api/user")
  void shouldCreateValidUserViaHTTP() {

    CreateUserInput validUser = UserTestFactory.mountValidUser();

    String jsonBody = this.jsonString.fromObj(validUser);
    log.info("JSON enviado teste sucesso: {}", jsonBody);

    given()
        .contentType(ContentType.JSON)
        .body(jsonBody)
        .when()
        .post("/user")
        .then()
        .statusCode(anyOf(is(HttpStatus.CREATED.value()), is(HttpStatus.OK.value())))
        .body("name", notNullValue())
        .body("email", notNullValue());

  }

  @Test
  @DisplayName("Tenta criar um user invalido via POST /api/user")
  void shouldCreateInvalidUserViaHTTP() {

    CreateUserInput invalidUser = UserTestFactory.mountInvalidUser();

    String jsonBody = this.jsonString.fromObj(invalidUser);
    log.info("JSON enviado no Teste de erro: {}", jsonBody);

    given()
        .contentType(ContentType.JSON)
        .body(jsonBody)
        .when()
        .post("/user")
        .then()
        .statusCode(anyOf(is(HttpStatus.BAD_REQUEST.value()), is(HttpStatus.FORBIDDEN.value())))
        .body("message", notNullValue())
        .body("name", nullValue())
        .body("email", nullValue());

  }

}