package com.imo.backend.contexts.identity.usecases;

import com.imo.backend.contexts.identity.User;
import com.imo.backend.contexts.identity.events.ForgetPasswordEvent;
import com.imo.backend.contexts.identity.repositories.UserRepository;
import com.imo.backend.contexts.common.exceptions.custom.NotFoundException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class SendForgetPasswordCodeUseCase {
  private final UserRepository userRepository;

  private final ApplicationEventPublisher applicationEventPublisher;

  public SendForgetPasswordCodeUseCase(
      UserRepository userRepository,
      ApplicationEventPublisher applicationEventPublisher
  ) {
    this.userRepository = userRepository;
    this.applicationEventPublisher = applicationEventPublisher;
  }

  public User execute(String email) {
    var foundUser = this.userRepository
        .findByEmail(email)
        .orElseThrow(() -> new NotFoundException(String.format("Email %s não encontrado", email)));

    Random random = new Random();
    var n = 1000 + random.nextInt(9000);
    var checkCode = String.valueOf(n);

    this.applicationEventPublisher.publishEvent(new ForgetPasswordEvent(
        foundUser.getId(),
        email,
        checkCode
    ));

    return foundUser;
  }
}
