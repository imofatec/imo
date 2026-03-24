package com.imo.backend.e2e.domains.auth;


import com.imo.backend.contexts.identity.user.commands.CreateUserCommand;
import com.imo.backend.contexts.identity.user.http.dtos.auth.LoginRequestDTO;
import com.imo.backend.e2e.config.BaseE2ETest;
import com.imo.backend.e2e.factories.UserTestFactory;
import com.imo.backend.e2e.utils.JsonString;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
class UserAuthenticationE2ETest extends BaseE2ETest {
  private final JsonString jsonString = new JsonString();

  @Test
  @DisplayName("Deve autenticar o usuario e retornar o JWT")
  void shouldAuthenticateUserAndReturnToken() {

    CreateUserCommand usuarioCriado = UserTestFactory.mountValidUser();
    String jsonBody = this.jsonString.fromObj(usuarioCriado);

    givenBaseRequest()
        .body(jsonBody)
        .post("/user")
        .then()
        .statusCode(anyOf(is(HttpStatus.CREATED.value()), is(HttpStatus.OK.value())))
        .body("name", notNullValue())
        .body("email", notNullValue());

    LoginRequestDTO loginRequestDTO = new LoginRequestDTO(
        usuarioCriado.email(),
        usuarioCriado.password()
    );

    String loginJson = this.jsonString.fromObj(loginRequestDTO);
    log.info("Login JSON enviado: {}", loginJson.toString());

    Response response = givenBaseRequest().body(loginJson).post("/user/login");

    response.then().statusCode(HttpStatus.OK.value()).body("accessToken", notNullValue());

    String token = response.jsonPath().getString("accessToken");
    log.info("Token: {}", token.toString());
    assertNotNull(token);
    assertTrue(token.startsWith("ey"));
  }
}
