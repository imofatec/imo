package com.imo.backend.contexts.identity.user.http.controllers.update;

import com.imo.backend.contexts.identity.user.http.controllers.UserController;
import com.imo.backend.contexts.identity.user.http.dtos.UserDTO;
import com.imo.backend.contexts.identity.user.usecases.UpdateUserAccessByIdUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UpdateUserAccessController extends UserController {

  private final UpdateUserAccessByIdUseCase accessByIdUseCase;

  public UpdateUserAccessController(UpdateUserAccessByIdUseCase accessByIdUseCase) {
    this.accessByIdUseCase = accessByIdUseCase;
  }

  @Operation(summary = "Confirm user registration")
  @SecurityRequirement(name = "Authorization")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Registro confirmado com sucesso",
            content = @Content(schema = @Schema(implementation = UserDTO.class))),
        @ApiResponse(
            responseCode = "401",
            description = "Não autenticado",
            content =
                @Content(
                    schema =
                        @Schema(
                            implementation =
                                com.imo.backend.contexts.common.exceptions.ErrorResponseDto
                                    .class))),
        @ApiResponse(
            responseCode = "404",
            description = "Usuário não encontrado",
            content =
                @Content(
                    schema =
                        @Schema(
                            implementation =
                                com.imo.backend.contexts.common.exceptions.ErrorResponseDto.class)))
      })
  @PutMapping("/confirm")
  public ResponseEntity<UserDTO> handle() {
    String userId = SecurityContextHolder.getContext().getAuthentication().getName();

    var updatedUser = UserDTO.fromUser(accessByIdUseCase.execute(userId));
    return ResponseEntity.ok(updatedUser);
  }
}
