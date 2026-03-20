package com.imo.backend.contexts.identity.http.controllers.auth;

import com.imo.backend.contexts.identity.http.controllers.UserController;
import com.imo.backend.contexts.identity.http.dtos.UserDTO;
import com.imo.backend.contexts.identity.usecases.VerifyForgetPasswordCodeUseCase;
import com.imo.backend.contexts.common.MongoDB;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VerifyForgetPasswordCodeController extends UserController {
  private final VerifyForgetPasswordCodeUseCase verifyForgetPasswordCodeUseCase;

  public VerifyForgetPasswordCodeController(VerifyForgetPasswordCodeUseCase verifyForgetPasswordCodeUseCase) {
    this.verifyForgetPasswordCodeUseCase = verifyForgetPasswordCodeUseCase;
  }

  @Operation(summary = "Verify the code sent to the user's email")
  @GetMapping("/forget-password/verify/{userId}/{code}")
  public ResponseEntity<UserDTO> handle(
      @PathVariable
      String userId,
      @PathVariable
      String code
  ) {
    MongoDB.validateObjectId(userId);

    var user = UserDTO.fromUser(this.verifyForgetPasswordCodeUseCase.execute(userId, code));
    return ResponseEntity.ok(user);
  }
}
