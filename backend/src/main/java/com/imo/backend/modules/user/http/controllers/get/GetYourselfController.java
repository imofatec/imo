package com.imo.backend.modules.user.http.controllers.get;

import com.imo.backend.modules.user.http.controllers.UserController;
import com.imo.backend.modules.user.http.dtos.UserDTO;
import com.imo.backend.modules.user.guards.GetUserByIdGuard;
import com.imo.backend.lib.token.TokenManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetYourselfController extends UserController {

  private final GetUserByIdGuard getUserByIdGuard;

  public GetYourselfController(GetUserByIdGuard getUserByIdGuard, TokenManager tokenManager) {
    this.getUserByIdGuard = getUserByIdGuard;
  }

  @Operation(summary = "Get your profile")
  @SecurityRequirement(name = "Authorization")
  @GetMapping("/profile")
  public ResponseEntity<UserDTO> handle() {
    var userId = SecurityContextHolder.getContext().getAuthentication().getName();
    var user = UserDTO.fromUser(this.getUserByIdGuard.execute(userId));

    return ResponseEntity.ok(user);
  }
}
