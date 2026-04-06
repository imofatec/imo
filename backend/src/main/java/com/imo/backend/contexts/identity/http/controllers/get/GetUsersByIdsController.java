package com.imo.backend.contexts.identity.http.controllers.get;

import com.imo.backend.contexts.common.MongoDB;
import com.imo.backend.contexts.identity.guards.GetUsersByIdsGuard;
import com.imo.backend.contexts.identity.http.controllers.UserController;
import com.imo.backend.contexts.identity.http.dtos.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetUsersByIdsController extends UserController {
  private final GetUsersByIdsGuard getUsersByIdsGuard;

  public GetUsersByIdsController(GetUsersByIdsGuard getUsersByIdsGuard) {
    this.getUsersByIdsGuard = getUsersByIdsGuard;
  }

  @Operation(summary = "Get user by ids")
  @GetMapping("/ids")
  public ResponseEntity<List<UserDTO>> handle(@RequestParam List<String> ids) {
    ids.forEach(MongoDB::validateObjectId);
    var users = this.getUsersByIdsGuard.execute(ids).stream().map(UserDTO::fromUser).toList();
    return ResponseEntity.ok(users);
  }
}
