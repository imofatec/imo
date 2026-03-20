package com.imo.backend.contexts.identity.services.impl;

import com.imo.backend.contexts.identity.User;
import com.imo.backend.contexts.identity.UserPolicies;
import com.imo.backend.contexts.identity.commands.CreateUserCommand;
import com.imo.backend.contexts.identity.events.SendEmailConfirmationEvent;
import com.imo.backend.contexts.identity.http.dtos.UserDTO;
import com.imo.backend.contexts.identity.repositories.UserRepository;
import com.imo.backend.contexts.identity.services.CreateUserService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateUserServiceImpl implements CreateUserService {

  private final ApplicationEventPublisher publisher;

  private final UserPolicies userPolicies;

  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  public CreateUserServiceImpl(
      ApplicationEventPublisher publisher,
      UserPolicies userPolicies,
      UserRepository userRepository,
      PasswordEncoder passwordEncoder
  ) {
    this.publisher = publisher;
    this.userPolicies = userPolicies;
    this.userRepository = null;
    this.passwordEncoder = null;
  }

  @Override
  public User execute(CreateUserCommand createUserCommand) {

        this.userPolicies.assertCanRegister(createUserCommand);

    var potentialNewUser = new User(
        createUserCommand.name(),
        createUserCommand.email(),
        createUserCommand.password(),
        false
    );

    potentialNewUser.setPassword(passwordEncoder.encode(potentialNewUser.getPassword()));

    User newUser = userRepository.save(potentialNewUser);
    this.publisher.publishEvent(new SendEmailConfirmationEvent(UserDTO.fromUser(newUser)));

    return newUser;
  }
}
