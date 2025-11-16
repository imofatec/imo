package com.imo.backend.modules.user.http.controllers.create;

import com.imo.backend.modules.user.actions.inputs.CreateUserInput;
import com.imo.backend.modules.user.http.controllers.UserController;
import com.imo.backend.modules.user.http.dtos.UserDTO;
import com.imo.backend.modules.user.services.CreateUserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreateUserController extends UserController {

  private final CreateUserService createUserService;

  public CreateUserController(CreateUserService createUserService) {
    this.createUserService = createUserService;
  }

  @Transactional
  @Operation(summary = "Register user")
  @PostMapping()
  public ResponseEntity<UserDTO> handle(
      @Valid
      @RequestBody
      CreateUserInput createUserInput
  ) {
    var newUser = UserDTO.fromUser(this.createUserService.execute(createUserInput));

    return new ResponseEntity<>(newUser, HttpStatus.CREATED);
  }
}
