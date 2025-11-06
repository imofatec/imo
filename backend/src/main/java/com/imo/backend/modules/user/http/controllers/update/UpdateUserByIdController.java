package com.imo.backend.modules.user.http.controllers.update;

import com.imo.backend.modules.user.http.controllers.UserController;
import com.imo.backend.modules.user.http.dtos.UpdateUserByIdRequest;
import com.imo.backend.modules.user.http.dtos.UserDTO;
import com.imo.backend.modules.user.services.UpdateUserByIdService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.stream.Stream;

@Slf4j
@RestController
public class UpdateUserByIdController extends UserController {
  private final UpdateUserByIdService updateUserByIdService;

  public UpdateUserByIdController(
      UpdateUserByIdService updateUserByIdService
  ) {
    this.updateUserByIdService = updateUserByIdService;
  }

  @Operation(summary = "Update the user's credentials")
  @SecurityRequirement(name = "Authorization")
  @PutMapping()
  public ResponseEntity<UserDTO> handle(
      @Valid
      @RequestBody
      UpdateUserByIdRequest fieldsToUpdateUser
  ) {

    String userId = SecurityContextHolder.getContext().getAuthentication().getName();

    return checkNoContent(fieldsToUpdateUser)
        ? ResponseEntity.noContent().build()
        : ResponseEntity.ok(UserDTO.fromUser(this.updateUserByIdService.execute(
            userId,
            fieldsToUpdateUser
        )));
  }

  private static boolean checkNoContent(UpdateUserByIdRequest fieldsToUpdateUser) {
    return Stream.of(fieldsToUpdateUser).allMatch(Objects::isNull);
  }
}
