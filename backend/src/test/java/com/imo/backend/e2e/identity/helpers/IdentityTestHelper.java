package com.imo.backend.e2e.identity.helpers;

import static io.restassured.RestAssured.given;

import com.imo.backend.contexts.identity.user.http.dtos.CreateUserRequest;
import com.imo.backend.contexts.identity.user.http.dtos.auth.LoginRequestDTO;
import io.restassured.http.ContentType;

public final class IdentityTestHelper {

  public record TestUser(String name, String email, String password) {
    public static TestUser defaultUser() {
      return new TestUser("Nome teste", "email@email.com", "Teste123");
    }

    public CreateUserRequest toCreateRequest() {
      return new CreateUserRequest(name, email, password, password);
    }

    public CreateUserRequest toCreateRequest(String confPassword) {
      return new CreateUserRequest(name, email, password, confPassword);
    }

    public LoginRequestDTO toLoginRequest() {
      return new LoginRequestDTO(email, password);
    }
  }

  public static String registerUser(CreateUserRequest request) {
    given().contentType(ContentType.JSON).body(request).when().post("/api/user");

    return request.email();
  }

  public static String login(LoginRequestDTO request) {
    return given()
        .contentType(ContentType.JSON)
        .body(request)
        .when()
        .post("/api/user/login")
        .then()
        .extract()
        .path("accessToken");
  }

  public static String registerAndLogin(TestUser user) {
    registerUser(user.toCreateRequest());
    return login(user.toLoginRequest());
  }
}
