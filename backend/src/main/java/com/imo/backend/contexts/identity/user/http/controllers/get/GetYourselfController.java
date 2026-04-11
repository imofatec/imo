package com.imo.backend.contexts.identity.user.http.controllers.get;

import com.imo.backend.contexts.identity.user.http.controllers.UserController;
import com.imo.backend.contexts.identity.user.http.dtos.UserDTO;
import com.imo.backend.contexts.identity.user.repositories.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetYourselfController extends UserController {

  private final UserRepository userRepository;

  public GetYourselfController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Operation(summary = "Get your profile")
  @SecurityRequirement(name = "Authorization")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Perfil do usuário",
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
  @GetMapping("/profile")
  public ResponseEntity<UserDTO> handle() {
    var userId = SecurityContextHolder.getContext().getAuthentication().getName();
    var user = UserDTO.fromUser(this.userRepository.findByIdOrThrow(userId));

    return ResponseEntity.ok(user);
  }
}
