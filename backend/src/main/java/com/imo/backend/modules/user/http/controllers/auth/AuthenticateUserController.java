package com.imo.backend.modules.user.http.controllers.auth;

import com.imo.backend.modules.user.http.controllers.UserController;
import com.imo.backend.modules.user.http.dtos.auth.LoginRequestDTO;
import com.imo.backend.modules.user.http.dtos.auth.LoginResponseDTO;
import com.imo.backend.modules.user.services.AuthenticateUserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticateUserController extends UserController {
  private final AuthenticateUserService authenticateUserService;

  public AuthenticateUserController(AuthenticateUserService authenticateUserService) {
    this.authenticateUserService = authenticateUserService;
  }

  @Operation(summary = "Login user")
  @PostMapping("/login")
  public ResponseEntity<LoginResponseDTO> handle(
      @Valid
      @RequestBody
      LoginRequestDTO loginRequestDTO
  ) {
    var token = authenticateUserService.execute(loginRequestDTO);
    return ResponseEntity.ok(token);
  }

}
