package com.imo.backend.contexts.identity.http.controllers.get;

import com.imo.backend.contexts.identity.http.controllers.UserController;
import com.imo.backend.contexts.identity.http.dtos.UserDTO;
import com.imo.backend.contexts.identity.repositories.UserRepository;
import com.imo.backend.contexts.common.MongoDB;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetUserByIdController extends UserController {
  private final UserRepository userRepository;

  public GetUserByIdController(UserRepository userRepository){
    this.userRepository = userRepository;
  }

  @Operation(summary = "Get user by id")
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
