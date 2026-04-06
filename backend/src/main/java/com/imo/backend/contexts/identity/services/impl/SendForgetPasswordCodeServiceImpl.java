package com.imo.backend.contexts.identity.services.impl;

import com.imo.backend.contexts.common.exceptions.custom.NotFoundException;
import com.imo.backend.contexts.identity.User;
import com.imo.backend.contexts.identity.events.ForgetPasswordEvent;
import com.imo.backend.contexts.identity.repositories.UserRepository;
import com.imo.backend.contexts.identity.services.SendForgetPasswordCodeService;
import java.util.Random;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class SendForgetPasswordCodeServiceImpl implements SendForgetPasswordCodeService {
  private final UserRepository userRepository;

  private final ApplicationEventPublisher applicationEventPublisher;

  public SendForgetPasswordCodeServiceImpl(
      UserRepository userRepository, ApplicationEventPublisher applicationEventPublisher) {
    this.userRepository = userRepository;
    this.applicationEventPublisher = applicationEventPublisher;
  }

  public User execute(String email) {
    var foundUser =
        this.userRepository
            .findByEmail(email)
            .orElseThrow(
                () -> new NotFoundException(String.format("Email %s não encontrado", email)));

    Random random = new Random();
    var n = 1000 + random.nextInt(9000);
    var checkCode = String.valueOf(n);

    this.applicationEventPublisher.publishEvent(
        new ForgetPasswordEvent(foundUser.getId(), email, checkCode));

    return foundUser;
  }
}
