package com.imo.backend.contexts.identity.http.controllers.get;

import com.imo.backend.contexts.identity.http.controllers.UserController;
import com.imo.backend.contexts.identity.http.dtos.UserDTO;
import com.imo.backend.contexts.identity.repositories.UserRepository;
import com.imo.backend.contexts.common.MongoDB;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetUsersByIdsController extends UserController {
  private final UserRepository userRepository;

  public GetUsersByIdsController(UserRepository userRepository){
    this.userRepository = userRepository;
  }

  @Operation(summary = "Get user by ids")
  @GetMapping("/ids")
  public ResponseEntity<List<UserDTO>> handle(
      @RequestParam
      List<String> ids
  ) {
    ids.forEach(MongoDB::validateObjectId); // Qual diferença desse pro ids.forEach(ValidateObjectId::execute);
    var users = this.userRepository.findByIds(ids).stream().map(UserDTO::fromUser).toList();
    return ResponseEntity.ok(users);
  }
}
