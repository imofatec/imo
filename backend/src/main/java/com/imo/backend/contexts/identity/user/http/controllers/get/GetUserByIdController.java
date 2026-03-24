package com.imo.backend.contexts.identity.user.http.controllers.get;

import com.imo.backend.contexts.common.MongoDB;
import com.imo.backend.contexts.identity.user.http.controllers.UserController;
import com.imo.backend.contexts.identity.user.http.dtos.UserDTO;
import com.imo.backend.contexts.identity.user.repositories.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetUserByIdController extends UserController {
  private final UserRepository userRepository;

  public GetUserByIdController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Operation(summary = "Get user by id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Usuário encontrado",
          content = @Content(schema = @Schema(implementation = UserDTO.class))),
      @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
          content = @Content(schema = @Schema(implementation = com.imo.backend.contexts.common.exceptions.ErrorResponseDto.class)))
  })
  @GetMapping("/{id}")
  public ResponseEntity<UserDTO> handle(
      @PathVariable
      String id
  ) {
    MongoDB.validateObjectId(id);
    var user = UserDTO.fromUser(this.userRepository.findByIdOrThrow(id));

    return ResponseEntity.ok(user);
  }
}
