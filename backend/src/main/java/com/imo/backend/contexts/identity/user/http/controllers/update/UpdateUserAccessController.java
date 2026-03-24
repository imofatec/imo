package com.imo.backend.contexts.identity.user.http.controllers.update;


import com.imo.backend.contexts.identity.user.http.controllers.UserController;
import com.imo.backend.contexts.identity.user.http.dtos.UserDTO;
import com.imo.backend.contexts.identity.user.usecases.UpdateUserAccessByIdUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UpdateUserAccessController extends UserController {

  private final UpdateUserAccessByIdUseCase accessByIdUseCase;

  public UpdateUserAccessController(UpdateUserAccessByIdUseCase accessByIdUseCase) {
    this.accessByIdUseCase = accessByIdUseCase;

  }

  @Operation(summary = "Confirm user registration")
  @SecurityRequirement(name = "Authorization")
  @PutMapping("/confirm")
  public ResponseEntity<UserDTO> handle() {
    String userId = SecurityContextHolder.getContext().getAuthentication().getName();


    var updatedUser = UserDTO.fromUser(accessByIdUseCase.execute(userId));
    return ResponseEntity.ok(updatedUser);
  }
}
