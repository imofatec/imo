package com.imo.backend.e2e.identity;

import com.imo.backend.contexts.identity.recovery.http.dtos.ResetPasswordRequest;
import com.imo.backend.contexts.identity.recovery.http.dtos.SendRecoveryCodeRequest;
import com.imo.backend.contexts.identity.recovery.http.dtos.VerifyRecoveryCodeRequest;
import com.imo.backend.contexts.identity.recovery.lib.CodeGenerator;
import com.imo.backend.contexts.identity.user.http.dtos.auth.LoginRequestDTO;
import com.imo.backend.e2e.BaseE2ETest;
import com.imo.backend.e2e.identity.helpers.IdentityTestHelper;
import com.imo.backend.e2e.identity.helpers.IdentityTestHelper.TestUser;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

class RecoveryE2ETest extends BaseE2ETest {

  private static final String CODE = "123456";

  @MockitoBean
  private CodeGenerator codeGenerator;

  @BeforeEach
  void mockCodeGenerator() {
    when(codeGenerator.generate()).thenReturn(CODE);
  }

  private TestUser registerUser() {
    TestUser user = TestUser.defaultUser();
    IdentityTestHelper.registerUser(user.toCreateRequest());
    return user;
  }

  @Test
  @DisplayName("happy path (POST /api/recovery/password/send-code): retorna 200 quando envia código")
  void shouldSendRecoveryCode() {
    TestUser user = registerUser();

    given()
        .contentType(ContentType.JSON)
        .body(new SendRecoveryCodeRequest(user.email()))
        .when()
        .post("/api/recovery/password/send-code")
        .then()
        .statusCode(HttpStatus.OK.value())
        .contentType(ContentType.JSON)
        .body(
            "message",
            equalTo(
                "Se o email informado existir, você receberá uma mensagem com o código de recuperação")
        );
  }


  @Test
  @DisplayName("happy path (POST /api/recovery/password/verify): retorna 200 quando código e valido")
  void shouldVerifyRecoveryCode() {
    TestUser user = registerUser();

    given()
        .contentType(ContentType.JSON)
        .body(new SendRecoveryCodeRequest(user.email()))
        .when()
        .post("/api/recovery/password/send-code");

    given()
        .contentType(ContentType.JSON)
        .body(new VerifyRecoveryCodeRequest(user.email(), CODE))
        .when()
        .post("/api/recovery/password/verify")
        .then()
        .statusCode(HttpStatus.OK.value())
        .contentType(ContentType.JSON)
        .body("message", equalTo("Código validado com sucesso"));
  }

  @Test
  @DisplayName("happy path (PATCH /api/recovery/password/reset): retorna 200 quando senha e resetada")
  void shouldResetPassword() {
    TestUser user = registerUser();
    String newPassword = "Nova1234";

    given()
        .contentType(ContentType.JSON)
        .body(new SendRecoveryCodeRequest(user.email()))
        .when()
        .post("/api/recovery/password/send-code");

    given()
        .contentType(ContentType.JSON)
        .body(new ResetPasswordRequest(user.email(), newPassword, CODE))
        .when()
        .patch("/api/recovery/password/reset")
        .then()
        .statusCode(HttpStatus.OK.value())
        .contentType(ContentType.JSON)
        .body("message", equalTo("Senha atualizada com sucesso"));

    given()
        .contentType(ContentType.JSON)
        .body(user.toLoginRequest())
        .when()
        .post("/api/user/login")
        .then()
        .statusCode(HttpStatus.BAD_REQUEST.value());

    given()
        .contentType(ContentType.JSON)
        .body(new LoginRequestDTO(user.email(), newPassword))
        .when()
        .post("/api/user/login")
        .then()
        .statusCode(HttpStatus.OK.value());
  }

  @Test
  @DisplayName("happy path: (fluxo completo): enviar código → verificar → resetar senha")
  void shouldCompleteRecoveryFlow() {
    TestUser user = registerUser();
    String newPassword = "Nova1234";

    given()
        .contentType(ContentType.JSON)
        .body(new SendRecoveryCodeRequest(user.email()))
        .when()
        .post("/api/recovery/password/send-code")
        .then()
        .statusCode(HttpStatus.OK.value());

    given()
        .contentType(ContentType.JSON)
        .body(new VerifyRecoveryCodeRequest(user.email(), CODE))
        .when()
        .post("/api/recovery/password/verify")
        .then()
        .statusCode(HttpStatus.OK.value());

    given()
        .contentType(ContentType.JSON)
        .body(new ResetPasswordRequest(user.email(), newPassword, CODE))
        .when()
        .patch("/api/recovery/password/reset")
        .then()
        .statusCode(HttpStatus.OK.value());

    given()
        .contentType(ContentType.JSON)
        .body(new LoginRequestDTO(user.email(), newPassword))
        .when()
        .post("/api/user/login")
        .then()
        .statusCode(HttpStatus.OK.value());
  }
}
