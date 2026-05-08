package com.imo.backend.contexts.identity.user.http.controllers.auth;

import com.imo.backend.contexts.identity.user.http.controllers.UserController;
import com.imo.backend.contexts.identity.user.http.dtos.auth.LoginRequestDTO;
import com.imo.backend.contexts.identity.user.http.dtos.auth.LoginResponseDTO;
import com.imo.backend.contexts.identity.user.usecases.AuthenticateUserUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Login realizado com sucesso",
            content = @Content(schema = @Schema(implementation = LoginResponseDTO.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Credenciais inválidas",
            content =
                @Content(
                    schema =
                        @Schema(
                            implementation =
                                com.imo.backend.contexts.common.exceptions.ErrorResponseDto.class)))
      })
  @PostMapping("/login")
  public ResponseEntity<LoginResponseDTO> handle(
      @Valid @RequestBody LoginRequestDTO loginRequestDTO) {
    var token = this.authenticateUserUseCase.execute(loginRequestDTO);
    return ResponseEntity.ok(token);
  }
}
