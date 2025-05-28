package com.imo.backend.controllers.user.patch;

import com.imo.backend.controllers.user.UserController;
import com.imo.backend.models.user.dtos.NoPasswordUser;
import com.imo.backend.models.user.dtos.UpdatePasswordDto;
import com.imo.backend.models.user.services.forgot_password.interfaces.UpdateUserPasswordByEmailCodeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UpdateUserPasswordByEmailCodeController extends UserController {
  private final UpdateUserPasswordByEmailCodeService updateUserPasswordByEmailCodeService;

  public UpdateUserPasswordByEmailCodeController(
      UpdateUserPasswordByEmailCodeService updateUserPasswordByEmailCodeService
  ) {
    this.updateUserPasswordByEmailCodeService = updateUserPasswordByEmailCodeService;
  }

  @PatchMapping("/{emailCode}/{userId}")
  public ResponseEntity<NoPasswordUser> handle(
      @PathVariable String emailCode, @PathVariable String userId, @Valid @RequestBody UpdatePasswordDto dto) {
    var updatedUser = this.updateUserPasswordByEmailCodeService.execute(emailCode, userId, dto.getPassword());

    return ResponseEntity.ok(updatedUser);
  }
}
