package com.imo.backend.contexts.identity.http.controllers.auth;

import com.imo.backend.contexts.identity.http.controllers.UserController;
import com.imo.backend.contexts.identity.http.dtos.auth.LoginRequestDTO;
import com.imo.backend.contexts.identity.http.dtos.auth.LoginResponseDTO;
import com.imo.backend.contexts.identity.usecases.AuthenticateUserUseCase;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticateUserController extends UserController {
  private final AuthenticateUserUseCase authenticateUserUseCase;

  public AuthenticateUserController(AuthenticateUserUseCase authenticateUserUseCase) {
    this.authenticateUserUseCase = authenticateUserUseCase;
  }

  @Operation(summary = "Login user")
  @PostMapping("/login")
  public ResponseEntity<LoginResponseDTO> handle(
      @Valid
      @RequestBody
      LoginRequestDTO loginRequestDTO
  ) {
    var token = this.authenticateUserUseCase.execute(loginRequestDTO);
    return ResponseEntity.ok(token);
  }

}
