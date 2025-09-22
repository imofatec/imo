package com.imo.backend.modules.user.http.controllers.auth;

import com.imo.backend.modules.user.http.controllers.UserController;
import com.imo.backend.modules.user.http.dtos.UserDTO;
import com.imo.backend.modules.user.services.SendForgetPasswordCodeService;
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
  public ResponseEntity<UserDTO> handle(
      @PathVariable
      String email
  ) {
    return ResponseEntity.ok(UserDTO.fromUser(this.sendForgetPasswordCodeService.execute(email)));
  }
}
