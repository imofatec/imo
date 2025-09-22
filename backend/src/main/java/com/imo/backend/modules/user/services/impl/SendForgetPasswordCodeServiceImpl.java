package com.imo.backend.modules.user.services.impl;

import com.imo.backend.exceptions.custom.NotFoundException;
import com.imo.backend.modules.user.User;
import com.imo.backend.modules.user.events.ForgetPasswordEvent;
import com.imo.backend.modules.user.repositories.UserRepository;
import com.imo.backend.modules.user.services.SendForgetPasswordCodeService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class SendForgetPasswordCodeServiceImpl implements SendForgetPasswordCodeService {
  private final UserRepository userRepository;

  private final ApplicationEventPublisher applicationEventPublisher;

  public SendForgetPasswordCodeServiceImpl(
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
