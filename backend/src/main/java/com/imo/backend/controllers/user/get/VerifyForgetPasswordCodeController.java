package com.imo.backend.controllers.user.get;

import com.imo.backend.controllers.user.UserController;
import com.imo.backend.lib.ValidateObjectId;
import com.imo.backend.models.user.dtos.NoPasswordUser;
import com.imo.backend.models.user.services.forgot_password.interfaces.VerifyForgetPasswordCodeService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VerifyForgetPasswordCodeController extends UserController {
  private final VerifyForgetPasswordCodeService verifyForgetPasswordCodeService;

  public VerifyForgetPasswordCodeController(
      VerifyForgetPasswordCodeService verifyForgetPasswordCodeService
  ) {
    this.verifyForgetPasswordCodeService = verifyForgetPasswordCodeService;
  }

  @Operation(summary = "Verify the code sent to the user's email")
  @GetMapping("/forget-password/verify/{userId}/{code}")
  public ResponseEntity<NoPasswordUser> handle(
      @PathVariable String userId, @PathVariable String code) {
    ValidateObjectId.execute(userId);

    var user = this.verifyForgetPasswordCodeService.execute(userId, code);
    return ResponseEntity.ok(user);
  }
}
