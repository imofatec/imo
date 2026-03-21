package com.imo.backend.contexts.identity.http.controllers.create;

import com.imo.backend.contexts.identity.commands.CreateUserCommand;
import com.imo.backend.contexts.identity.http.controllers.UserController;
import com.imo.backend.contexts.identity.http.dtos.CreateUserRequest;
import com.imo.backend.contexts.identity.http.dtos.UserDTO;
import com.imo.backend.contexts.identity.usecases.CreateUserUseCase;
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

  private final CreateUserUseCase createUserUseCase;

  public CreateUserController(CreateUserUseCase createUserUseCase) {
    this.createUserUseCase = createUserUseCase;
  }

  @Transactional
  @Operation(summary = "Register user")
  @PostMapping()
  public ResponseEntity<UserDTO> handle(
      @Valid
      @RequestBody
      CreateUserRequest createUserRequest
  ) {
    var newUser = UserDTO.fromUser(this.createUserUseCase.execute(new CreateUserCommand(createUserRequest.name(), createUserRequest.email(), createUserRequest.password(), createUserRequest.confPassword())));

    return new ResponseEntity<>(newUser, HttpStatus.CREATED);
  }
}
