package com.imo.backend.contexts.identity.user.http.controllers.get;

import com.imo.backend.contexts.common.MongoDB;
import com.imo.backend.contexts.identity.user.http.controllers.UserController;
import com.imo.backend.contexts.identity.user.http.dtos.UserDTO;
import com.imo.backend.contexts.identity.user.repositories.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetUsersByIdsController extends UserController {
  private final UserRepository userRepository;

  public GetUsersByIdsController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Operation(summary = "Get user by ids")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Usuários encontrados",
            content =
                @Content(array = @ArraySchema(schema = @Schema(implementation = UserDTO.class))))
      })
  @GetMapping("/ids")
  public ResponseEntity<List<UserDTO>> handle(@RequestParam List<String> ids) {
    ids.forEach(
        MongoDB
            ::validateObjectId); // Qual diferença desse pro ids.forEach(ValidateObjectId::execute);
    var users = this.userRepository.findByIds(ids).stream().map(UserDTO::fromUser).toList();
    return ResponseEntity.ok(users);
  }
}
