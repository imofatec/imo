package com.imo.backend.contexts.identity.user.usecases;

import com.imo.backend.contexts.identity.user.User;
import com.imo.backend.contexts.identity.user.UserPolicies;
import com.imo.backend.contexts.identity.user.commands.CreateUserCommand;
import com.imo.backend.contexts.identity.user.events.SendEmailConfirmationEvent;
import com.imo.backend.contexts.identity.user.repositories.UserRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateUserUseCase {

  private final ApplicationEventPublisher publisher;

  private final UserPolicies userPolicies;

  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  public CreateUserUseCase(
      ApplicationEventPublisher publisher,
      UserPolicies userPolicies,
      UserRepository userRepository,
      PasswordEncoder passwordEncoder) {
    this.publisher = publisher;
    this.userPolicies = userPolicies;
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public User execute(CreateUserCommand cmd) {
    this.userPolicies.assertCanRegister(cmd);

    var potentialNewUser = new User(cmd.name(), cmd.email(), cmd.password(), false);

    potentialNewUser.setPassword(this.passwordEncoder.encode(potentialNewUser.getPassword()));

    User newUser = this.userRepository.save(potentialNewUser);
    this.publisher.publishEvent(
        new SendEmailConfirmationEvent(newUser.getEmail(), newUser.getName()));

    return newUser;
  }
}
