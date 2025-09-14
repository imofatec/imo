package com.imo.backend.modules.user.http.controllers.update;

import com.imo.backend.modules.user.actions.inputs.UpdatePasswordInput;
import com.imo.backend.modules.user.http.controllers.UserController;
import com.imo.backend.modules.user.http.dtos.UserDTO;
import com.imo.backend.modules.user.services.UpdatePasswordByEmailCodeService;
import com.imo.backend.utils.MongoDB;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UpdateUserPasswordByEmailCodeController extends UserController {
  private final UpdatePasswordByEmailCodeService updatePasswordByEmailCodeService;

  public UpdateUserPasswordByEmailCodeController(UpdatePasswordByEmailCodeService updatePasswordByEmailCodeService) {
    this.updatePasswordByEmailCodeService = updatePasswordByEmailCodeService;
  }

  @Operation(summary = "Update user password by email code")
  @PatchMapping("/{emailCode}/{userId}")
  public ResponseEntity<UserDTO> handle(
      @PathVariable
      String emailCode,
      @PathVariable
      String userId,
      @Valid
      @RequestBody
      UpdatePasswordInput dto
  ) {
    MongoDB.validateObjectId(userId);

    var updatedUser = this.updatePasswordByEmailCodeService.execute(
        emailCode,
        userId,
        dto.getPassword()
    );

    return ResponseEntity.ok(UserDTO.fromUser(updatedUser));
  }
}
