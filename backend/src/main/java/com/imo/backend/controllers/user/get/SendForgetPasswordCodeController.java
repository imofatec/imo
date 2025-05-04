package com.imo.backend.controllers.user.get;

import com.imo.backend.controllers.user.UserController;
import com.imo.backend.models.user.dtos.NoPasswordUser;
import com.imo.backend.models.user.services.forgot_password.interfaces.SendForgetPasswordCodeService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SendForgetPasswordCodeController extends UserController {
  private final SendForgetPasswordCodeService sendForgetPasswordCodeService;

  public SendForgetPasswordCodeController(SendForgetPasswordCodeService sendForgetPasswordCodeService) {
    this.sendForgetPasswordCodeService = sendForgetPasswordCodeService;
  }

  @Operation(summary = "Send code to user's email where he can change his password")
  @GetMapping("/forget-password/{email}")
  public ResponseEntity<NoPasswordUser> handle(@PathVariable String email) {
    var user = this.sendForgetPasswordCodeService.execute(email);
    return ResponseEntity.ok(user);
  }
}
