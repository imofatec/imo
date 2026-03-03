package com.imo.backend.contexts.identity.http.controllers.get;

import com.imo.backend.contexts.identity.guards.GetUserByIdGuard;
import com.imo.backend.contexts.identity.http.controllers.UserController;
import com.imo.backend.contexts.identity.http.dtos.UserDTO;
import com.imo.backend.contexts.common.MongoDB;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetUserByIdController extends UserController {
  private final GetUserByIdGuard getUserByIdGuard;

  private final ApplicationEventPublisher applicationEventPublisher;

  public GetUserByIdController(
      GetUserByIdGuard getUserByIdGuard,
      ApplicationEventPublisher applicationEventPublisher
  ) {
    this.getUserByIdGuard = getUserByIdGuard;
    this.applicationEventPublisher = applicationEventPublisher;
  }

  @Operation(summary = "Get user by id")
  @GetMapping("/{id}")
  public ResponseEntity<UserDTO> handle(
      @PathVariable
      String id
  ) {
    MongoDB.validateObjectId(id);
    var user = UserDTO.fromUser(this.getUserByIdGuard.execute(id));

    applicationEventPublisher.publishEvent("hello world" + "");
    return ResponseEntity.ok(user);
  }
}
