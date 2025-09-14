package com.imo.backend.modules.user.services.impl;

import com.imo.backend.exceptions.custom.NotFoundException;
import com.imo.backend.modules.user.User;
import com.imo.backend.modules.user.http.dtos.auth.ForgetPassword;
import com.imo.backend.modules.user.repositories.UserRepository;
import com.imo.backend.modules.user.services.SendForgetPasswordCodeService;
import com.imo.backend.outbox.Outbox;
import com.imo.backend.outbox.OutboxEvent;
import com.imo.backend.outbox.OutboxStatus;
import com.imo.backend.outbox.services.interfaces.ICreateOutboxService;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class SendForgetPasswordCodeServiceImpl implements SendForgetPasswordCodeService {
  private final UserRepository userRepository;

  private final ICreateOutboxService<ForgetPassword> createOutboxService;

  public SendForgetPasswordCodeServiceImpl(
      UserRepository userRepository,
      ICreateOutboxService<ForgetPassword> createOutboxService
  ) {
    this.userRepository = userRepository;
    this.createOutboxService = createOutboxService;
  }

  public User execute(String email) {
    var foundUser = this.userRepository
        .findByEmail(email)
        .orElseThrow(() -> new NotFoundException(String.format("Email %s não encontrado", email)));

    Random random = new Random();
    var n = 1000 + random.nextInt(9000);
    var checkCode = String.valueOf(n);

    var forgotPasswordOutbox = new ForgetPassword(
        foundUser.getId(),
        foundUser.getEmail(),
        checkCode
    );

    var newOutbox = new Outbox<>(
        forgotPasswordOutbox,
        OutboxStatus.PENDING,
        OutboxEvent.USER_FORGET_PASSWORD
    );

    this.createOutboxService.execute(newOutbox);

    return foundUser;
  }
}
