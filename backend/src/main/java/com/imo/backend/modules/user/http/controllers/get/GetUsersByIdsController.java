package com.imo.backend.modules.user.http.controllers.get;

import com.imo.backend.modules.user.http.controllers.UserController;
import com.imo.backend.modules.user.http.dtos.UserDTO;
import com.imo.backend.modules.user.guards.GetUsersByIdsGuard;
import com.imo.backend.utils.MongoDB;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetUsersByIdsController extends UserController {
  private final GetUsersByIdsGuard getUsersByIdsGuard;

  public GetUsersByIdsController(GetUsersByIdsGuard getUsersByIdsGuard) {
    this.getUsersByIdsGuard = getUsersByIdsGuard;
  }

  @Operation(summary = "Get user by ids")
  @GetMapping("/ids")
  public ResponseEntity<List<UserDTO>> handle(
      @RequestParam
      List<String> ids
  ) {
    ids.forEach(MongoDB::validateObjectId);
    var users = this.getUsersByIdsGuard.execute(ids).stream().map(UserDTO::fromUser).toList();
    return ResponseEntity.ok(users);
  }
}
