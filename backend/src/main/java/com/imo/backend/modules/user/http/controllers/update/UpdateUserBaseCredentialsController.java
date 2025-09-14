package com.imo.backend.modules.user.http.controllers.update;

import com.imo.backend.modules.user.actions.inputs.UpdateBaseCredentialsInput;
import com.imo.backend.modules.user.http.controllers.UserController;
import com.imo.backend.modules.user.http.dtos.UserDTO;
import com.imo.backend.modules.user.services.UpdateBaseCredentialsService;
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
public class UpdateUserBaseCredentialsController extends UserController {
  private final UpdateBaseCredentialsService updateBaseCredentialsService;

  public UpdateUserBaseCredentialsController(
      UpdateBaseCredentialsService updateBaseCredentialsService
  ) {
    this.updateBaseCredentialsService = updateBaseCredentialsService;
  }

  @Operation(summary = "Update the user's credentials")
  @SecurityRequirement(name = "Authorization")
  @PutMapping()
  public ResponseEntity<UserDTO> handle(
      @Valid
      @RequestBody
      UpdateBaseCredentialsInput fieldsToUpdateUser
  ) {

    String userId = SecurityContextHolder.getContext().getAuthentication().getName();

    return checkNoContent(fieldsToUpdateUser)
        ? ResponseEntity.noContent().build()
        : ResponseEntity.ok(UserDTO.fromUser(this.updateBaseCredentialsService.execute(
            userId,
            fieldsToUpdateUser
        )));
  }

  private static boolean checkNoContent(UpdateBaseCredentialsInput fieldsToUpdateUser) {
    return Stream
        .of(fieldsToUpdateUser.name(), fieldsToUpdateUser.password(), fieldsToUpdateUser.email())
        .allMatch(Objects::isNull);
  }
}
