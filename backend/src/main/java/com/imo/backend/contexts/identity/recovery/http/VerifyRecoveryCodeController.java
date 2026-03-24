package com.imo.backend.contexts.identity.recovery.http;

import com.imo.backend.contexts.common.SimpleMessage;
import com.imo.backend.contexts.identity.recovery.RecoveryCodePolicy;
import com.imo.backend.contexts.identity.recovery.http.dtos.VerifyRecoveryCodeRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VerifyRecoveryCodeController extends RecoveryController {
  private final RecoveryCodePolicy policy;

  public VerifyRecoveryCodeController(RecoveryCodePolicy policy) {
    this.policy = policy;
  }

  @PostMapping("/password/verify")
  public ResponseEntity<SimpleMessage> handle(
      @RequestBody
      VerifyRecoveryCodeRequest request
  ) {
    this.policy.assertCanRecovery(request.code(), request.email());

    return ResponseEntity.ok().body(new SimpleMessage("Código validado com sucesso"));
  }
}
