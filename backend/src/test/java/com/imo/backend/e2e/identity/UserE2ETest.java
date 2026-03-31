package com.imo.backend.e2e.identity;

import com.imo.backend.contexts.identity.user.http.dtos.UpdateUserByIdRequest;
import com.imo.backend.contexts.identity.user.http.dtos.UserDTO;
import com.imo.backend.contexts.identity.user.http.dtos.auth.LoginRequestDTO;
import com.imo.backend.contexts.identity.user.value_objects.AcademicDegree;
import com.imo.backend.contexts.identity.user.value_objects.AvailableTimePerDay;
import com.imo.backend.e2e.BaseE2ETest;
import com.imo.backend.e2e.identity.helpers.IdentityTestHelper;
import com.imo.backend.e2e.identity.helpers.IdentityTestHelper.TestUser;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;

class UserE2ETest extends BaseE2ETest {
  @Test
  @DisplayName("happy path (POST /api/user): retorna 201 quando cria usuário")
  void shouldCreateUser() {
    TestUser user = TestUser.defaultUser();

    UserDTO created = given()
        .contentType(ContentType.JSON)
        .body(user.toCreateRequest())
        .when()
        .post("/api/user")
        .then()
        .statusCode(HttpStatus.CREATED.value())
        .contentType(ContentType.JSON)
        .extract()
        .as(UserDTO.class);

    assertNotNull(created.id());
    assertEquals(user.name(), created.name());
    assertEquals(user.email(), created.email());
    assertFalse(created.isConfirmed());
  }

  @Test
  @DisplayName("exception (POST /api/user): retorna 400 quando senhas nao coincidem")
  void shouldReturn400WhenPasswordsDoNotMatch() {
    TestUser user = TestUser.defaultUser();

    given()
        .contentType(ContentType.JSON)
        .body(user.toCreateRequest("Diferente123"))
        .when()
        .post("/api/user")
        .then()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .contentType(ContentType.JSON)
        .body("error", equalTo("BAD_REQUEST"));
  }

  @Test
  @DisplayName("exception (POST /api/user): retorna 409 quando email ja existe")
  void shouldReturn409WhenEmailAlreadyExists() {
    TestUser user = TestUser.defaultUser();

    given().contentType(ContentType.JSON).body(user.toCreateRequest()).when().post("/api/user");

    given()
        .contentType(ContentType.JSON)
        .body(user.toCreateRequest())
        .when()
        .post("/api/user")
        .then()
        .statusCode(HttpStatus.CONFLICT.value())
        .contentType(ContentType.JSON)
        .body("error", equalTo("CONFLICT"));
  }

  @Test
  @DisplayName("happy path (POST /api/user/login): retorna 200 com token de acesso")
  void shouldLoginSuccessfully() {
    TestUser user = TestUser.defaultUser();
    IdentityTestHelper.registerUser(user.toCreateRequest());

    given()
        .contentType(ContentType.JSON)
        .body(user.toLoginRequest())
        .when()
        .post("/api/user/login")
        .then()
        .statusCode(HttpStatus.OK.value())
        .contentType(ContentType.JSON)
        .body("accessToken", notNullValue())
        .body("expiresIn", equalTo(18000));
  }

  @Test
  @DisplayName("exception (POST /api/user/login): retorna 400 quando credenciais são inválidas")
  void shouldReturn400WhenCredentialsAreInvalid() {
    given()
        .contentType(ContentType.JSON)
        .body(new LoginRequestDTO("inexistente@email.com", "Teste123"))
        .when()
        .post("/api/user/login")
        .then()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .contentType(ContentType.JSON)
        .body("error", equalTo("BAD_REQUEST"));
  }

  @Test
  @DisplayName("happy path (PUT /api/user/confirm): retorna 200 quando confirma conta do usuário")
  void shouldConfirmUser() {
    TestUser user = TestUser.defaultUser();
    String token = IdentityTestHelper.registerAndLogin(user);

    UserDTO foundUser = given()
        .header("Authorization", "Bearer " + token)
        .when()
        .put("/api/user/confirm")
        .then()
        .statusCode(HttpStatus.OK.value())
        .contentType(ContentType.JSON)
        .extract()
        .as(UserDTO.class);

    assertNotNull(foundUser.id());
    assertEquals(user.name(), foundUser.name());
    assertEquals(user.email(), foundUser.email());
    assertTrue(foundUser.isConfirmed());
  }

  @Test
  @DisplayName("happy path (GET /api/user/profile): retorna 200 com perfil do usuário autenticado")
  void shouldReturnProfileWhenAuthenticated() {
    TestUser user = TestUser.defaultUser();
    String token = IdentityTestHelper.registerAndLogin(user);

    UserDTO profile = given()
        .header("Authorization", "Bearer " + token)
        .when()
        .get("/api/user/profile")
        .then()
        .statusCode(HttpStatus.OK.value())
        .contentType(ContentType.JSON)
        .extract()
        .as(UserDTO.class);

    assertNotNull(profile.id());
    assertEquals(user.name(), profile.name());
    assertEquals(user.email(), profile.email());
    assertFalse(profile.isConfirmed());
  }

  @Test
  @DisplayName("exception (GET /api/user/profile): retorna erro quando nao autenticado")
  void shouldReturnErrorWhenNotAuthenticated() {
    given().when().get("/api/user/profile").then()
        .statusCode(HttpStatus.UNAUTHORIZED.value())
        .body("error", equalTo("UNAUTHORIZED"));
  }

  @Test
  @DisplayName("happy path (PUT /api/user): retorna 200 quando atualiza usuário")
  void shouldUpdateUser() {
    TestUser user = TestUser.defaultUser();
    String token = IdentityTestHelper.registerAndLogin(user);

    UpdateUserByIdRequest updateRequest = new UpdateUserByIdRequest(
        "updated@email.com",
        "Updated Name",
        null,
        "2026/03/31",
        AvailableTimePerDay.ONE_TO_TWO_HOURS,
        AcademicDegree.BACHELOR,
        null,
        null);

    UserDTO updated = given()
        .header("Authorization", "Bearer " + token)
        .contentType(ContentType.JSON)
        .body(updateRequest)
        .when()
        .put("/api/user")
        .then()
        .statusCode(HttpStatus.OK.value())
        .contentType(ContentType.JSON)
        .extract()
        .as(UserDTO.class);

    assertNotNull(updated.id());
    assertEquals("Updated Name", updated.name());
    assertEquals("updated@email.com", updated.email());
    assertEquals(AcademicDegree.BACHELOR, updated.academicDegree());
    assertEquals(AvailableTimePerDay.ONE_TO_TWO_HOURS, updated.availableTimePerDay());
  }

  @Test
  @DisplayName("happy path (PUT /api/user/profile-picture): retorna 200 quando faz upload de foto")
  void shouldUploadProfilePicture() throws IOException {
    TestUser user = TestUser.defaultUser();
    String token = IdentityTestHelper.registerAndLogin(user);

    File tempFile = File.createTempFile("profile", ".png");
    try (FileOutputStream fos = new FileOutputStream(tempFile)) {
      fos.write(new byte[] { (byte) 0x89, 0x50, 0x4E, 0x47 });
    }

    UserDTO updated = given()
        .header("Authorization", "Bearer " + token)
        .multiPart("file", tempFile, "image/png")
        .when()
        .put("/api/user/profile-picture")
        .then()
        .statusCode(HttpStatus.OK.value())
        .contentType(ContentType.JSON)
        .extract()
        .as(UserDTO.class);

    assertNotNull(updated.id());
    assertNotNull(updated.profilePicturePath());
    tempFile.delete();
  }
}
