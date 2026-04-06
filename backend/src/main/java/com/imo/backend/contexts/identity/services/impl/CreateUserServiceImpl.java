package com.imo.backend.contexts.identity.services.impl;

import com.imo.backend.contexts.identity.User;
import com.imo.backend.contexts.identity.actions.CreateUserAction;
import com.imo.backend.contexts.identity.actions.inputs.CreateUserInput;
import com.imo.backend.contexts.identity.events.SendEmailConfirmationEvent;
import com.imo.backend.contexts.identity.http.dtos.UserDTO;
import com.imo.backend.contexts.identity.services.CreateUserService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class CreateUserServiceImpl implements CreateUserService {
  private final CreateUserAction createUserAction;

  private final ApplicationEventPublisher publisher;

  public CreateUserServiceImpl(
      CreateUserAction createUserAction, ApplicationEventPublisher publisher) {
    this.createUserAction = createUserAction;
    this.publisher = publisher;
  }

  @Override
  public User execute(CreateUserInput input) {
    User newUser = this.createUserAction.execute(input);
    this.publisher.publishEvent(new SendEmailConfirmationEvent(UserDTO.fromUser(newUser)));

    return newUser;
  }
}
