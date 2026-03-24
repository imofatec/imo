package com.imo.backend.contexts.identity.recovery.http;

import com.imo.backend.contexts.common.SimpleMessage;
import com.imo.backend.contexts.identity.recovery.http.dtos.ResetPasswordRequest;
import com.imo.backend.contexts.identity.recovery.usecases.RecoveryPasswordUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResetPasswordController extends RecoveryController {
  private final RecoveryPasswordUseCase useCase;

  public ResetPasswordController(RecoveryPasswordUseCase useCase) {
    this.useCase = useCase;
  }

  @PatchMapping("/password/reset")
  public ResponseEntity<SimpleMessage> handle(
      @RequestBody
      ResetPasswordRequest request
  ) {

    String response = this.useCase.execute(
        request.email(),
        request.newPassword(),
        request.code()
    );

    return ResponseEntity.ok().body(new SimpleMessage(response));
  }
}
