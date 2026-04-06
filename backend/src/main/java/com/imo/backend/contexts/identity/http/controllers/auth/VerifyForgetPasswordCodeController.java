package com.imo.backend.contexts.identity.http.controllers.auth;

import com.imo.backend.contexts.common.MongoDB;
import com.imo.backend.contexts.identity.http.controllers.UserController;
import com.imo.backend.contexts.identity.http.dtos.UserDTO;
import com.imo.backend.contexts.identity.services.VerifyForgetPasswordCodeService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VerifyForgetPasswordCodeController extends UserController {
  private final VerifyForgetPasswordCodeService verifyForgetPasswordCodeService;

  public VerifyForgetPasswordCodeController(
      VerifyForgetPasswordCodeService verifyForgetPasswordCodeService) {
    this.verifyForgetPasswordCodeService = verifyForgetPasswordCodeService;
  }

  @Operation(summary = "Verify the code sent to the user's email")
  @GetMapping("/forget-password/verify/{userId}/{code}")
  public ResponseEntity<UserDTO> handle(@PathVariable String userId, @PathVariable String code) {
    MongoDB.validateObjectId(userId);

    var user = UserDTO.fromUser(this.verifyForgetPasswordCodeService.execute(userId, code));
    return ResponseEntity.ok(user);
  }
}
