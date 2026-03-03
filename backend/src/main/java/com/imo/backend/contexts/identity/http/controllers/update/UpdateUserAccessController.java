package com.imo.backend.contexts.identity.http.controllers.update;

import com.imo.backend.contexts.identity.actions.UpdateUserAccessByIdAction;
import com.imo.backend.contexts.identity.http.controllers.UserController;
import com.imo.backend.contexts.identity.http.dtos.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UpdateUserAccessController extends UserController {

  private final UpdateUserAccessByIdAction updateUserAccessByIdAction;

  public UpdateUserAccessController(UpdateUserAccessByIdAction updateUserAccessByIdAction) {
    this.updateUserAccessByIdAction = updateUserAccessByIdAction;
  }

  @Operation(summary = "Confirm user registration")
  @SecurityRequirement(name = "Authorization")
  @PutMapping("/confirm")
  public ResponseEntity<UserDTO> handle() {
    String userId = SecurityContextHolder.getContext().getAuthentication().getName();
    var updatedUser = UserDTO.fromUser(this.updateUserAccessByIdAction.execute(userId));
    return ResponseEntity.ok(updatedUser);
  }
}
