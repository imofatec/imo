package com.imo.backend.contexts.identity.http.controllers.update;

import com.imo.backend.contexts.identity.http.controllers.UserController;
import com.imo.backend.contexts.identity.http.dtos.UpdatePasswordRequest;
import com.imo.backend.contexts.identity.http.dtos.UserDTO;
import com.imo.backend.contexts.identity.usecases.UpdatePasswordByEmailCodeUseCase;
import com.imo.backend.contexts.common.MongoDB;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UpdateUserPasswordByEmailCodeController extends UserController {
  private final UpdatePasswordByEmailCodeUseCase updatePasswordByEmailCodeUseCase;

  public UpdateUserPasswordByEmailCodeController(UpdatePasswordByEmailCodeUseCase updatePasswordByEmailCodeUseCase) {
    this.updatePasswordByEmailCodeUseCase = updatePasswordByEmailCodeUseCase;
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
      UpdatePasswordRequest dto
  ) {
    MongoDB.validateObjectId(userId);

    var updatedUser = this.updatePasswordByEmailCodeUseCase.execute(
        emailCode,
        userId,
        dto.getPassword()
    );

    return ResponseEntity.ok(UserDTO.fromUser(updatedUser));
  }
}
