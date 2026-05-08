package com.imo.backend.contexts.identity.user.usecases;

import com.imo.backend.contexts.identity.user.User;
import com.imo.backend.contexts.identity.user.events.SendEmailConfirmationEvent;
import com.imo.backend.contexts.identity.user.repositories.UserRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class ResendConfirmationEmailUseCase {
  private final UserRepository repository;

  private final ApplicationEventPublisher publisher;

  public ResendConfirmationEmailUseCase(
      UserRepository repository, ApplicationEventPublisher publisher) {
    this.repository = repository;
    this.publisher = publisher;
  }

  public void execute(String email) {
    User foundUser = this.repository.findByEmail(email).orElse(null);

    if (foundUser == null || foundUser.getIsConfirmed()) {
      return;
    }

    this.publisher.publishEvent(
        new SendEmailConfirmationEvent(foundUser.getEmail(), foundUser.getName()));
  }
}
