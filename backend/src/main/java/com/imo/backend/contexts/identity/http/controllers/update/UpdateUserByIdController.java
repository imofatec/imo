package com.imo.backend.contexts.identity.http.controllers.update;

import com.imo.backend.contexts.identity.http.controllers.UserController;
import com.imo.backend.contexts.identity.http.dtos.UpdateUserByIdRequest;
import com.imo.backend.contexts.identity.http.dtos.UserDTO;
import com.imo.backend.contexts.identity.services.UpdateUserByIdService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import java.util.Objects;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UpdateUserByIdController extends UserController {
  private final UpdateUserByIdService updateUserByIdService;

  public UpdateUserByIdController(UpdateUserByIdService updateUserByIdService) {
    this.updateUserByIdService = updateUserByIdService;
  }

  @Operation(summary = "Update the user's credentials")
  @SecurityRequirement(name = "Authorization")
  @PutMapping()
  public ResponseEntity<UserDTO> handle(
      @Valid @RequestBody UpdateUserByIdRequest fieldsToUpdateUser) {

    String userId = SecurityContextHolder.getContext().getAuthentication().getName();

    return checkNoContent(fieldsToUpdateUser)
        ? ResponseEntity.noContent().build()
        : ResponseEntity.ok(
            UserDTO.fromUser(this.updateUserByIdService.execute(userId, fieldsToUpdateUser)));
  }

  private static boolean checkNoContent(UpdateUserByIdRequest fieldsToUpdateUser) {
    return Stream.of(fieldsToUpdateUser).allMatch(Objects::isNull);
  }
}
