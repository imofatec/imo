package com.imo.backend.modules.user.http.controllers.create;

import com.imo.backend.modules.user.actions.impl.CreateUserActionImpl;
import com.imo.backend.modules.user.actions.inputs.CreateUserInput;
import com.imo.backend.modules.user.http.controllers.UserController;
import com.imo.backend.modules.user.http.dtos.UserDTO;
import com.imo.backend.outbox.Outbox;
import com.imo.backend.outbox.OutboxEvent;
import com.imo.backend.outbox.OutboxStatus;
import com.imo.backend.outbox.services.interfaces.ICreateOutboxService;
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

  private final CreateUserActionImpl createUserCommandImpl;

  private final ICreateOutboxService<UserDTO> createOutboxService;

  public CreateUserController(
      CreateUserActionImpl createUserCommandImpl,
      ICreateOutboxService<UserDTO> createOutboxService
  ) {
    this.createUserCommandImpl = createUserCommandImpl;
    this.createOutboxService = createOutboxService;
  }

  @Transactional
  @Operation(summary = "Register user")
  @PostMapping()
  public ResponseEntity<UserDTO> handle(
      @Valid
      @RequestBody
      CreateUserInput createUserInput
  ) {
    var newUser = UserDTO.fromUser(this.createUserCommandImpl.execute(createUserInput));

    var userOutbox = new Outbox<>(
        newUser,
        OutboxStatus.PENDING,
        OutboxEvent.USER_EMAIL_CONFIRMATION
    );

    createOutboxService.execute(userOutbox);

    return new ResponseEntity<>(newUser, HttpStatus.CREATED);
  }
}
