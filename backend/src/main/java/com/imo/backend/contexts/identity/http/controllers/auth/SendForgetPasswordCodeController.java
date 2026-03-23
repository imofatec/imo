package com.imo.backend.contexts.identity.http.controllers.auth;

import com.imo.backend.contexts.identity.http.controllers.UserController;
import com.imo.backend.contexts.identity.http.dtos.SimpleMessage;
import com.imo.backend.contexts.identity.http.dtos.auth.ForgetPasswordRequest;
import com.imo.backend.contexts.identity.usecases.SendForgetPasswordCodeUseCase;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SendForgetPasswordCodeController extends UserController {
  private final SendForgetPasswordCodeUseCase sendForgetPasswordCodeUseCase;

  public SendForgetPasswordCodeController(SendForgetPasswordCodeUseCase sendForgetPasswordCodeUseCase) {
    this.sendForgetPasswordCodeUseCase = sendForgetPasswordCodeUseCase;
  }

  @Operation(summary = "Send code to user's email where he can change his password")
  @PostMapping("/forget-password")
  public ResponseEntity<SimpleMessage> handle(
      @RequestBody ForgetPasswordRequest request) {
    String response = this.sendForgetPasswordCodeUseCase.execute(request.email());
    return ResponseEntity.ok(new SimpleMessage(response));
  }
}
