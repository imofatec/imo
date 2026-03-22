package com.imo.backend.contexts.identity.http.controllers.auth;

import com.imo.backend.contexts.identity.http.controllers.UserController;
import com.imo.backend.contexts.identity.http.dtos.SimpleMessage;
import com.imo.backend.contexts.identity.http.dtos.auth.RecoveryPasswordRequest;
import com.imo.backend.contexts.identity.usecases.RecoveryPasswordUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecoveryPasswordController extends UserController {
  private final RecoveryPasswordUseCase useCase;

  public RecoveryPasswordController(RecoveryPasswordUseCase useCase) {
    this.useCase = useCase;
  }

  @PatchMapping("/recovery-password")
  public ResponseEntity<SimpleMessage> handle(
      @RequestBody
      RecoveryPasswordRequest request
  ) {

    String response = this.useCase.execute(request.email(), request.newPassword(), request.code());

    return ResponseEntity.ok().body(new SimpleMessage(response));
  }
}
