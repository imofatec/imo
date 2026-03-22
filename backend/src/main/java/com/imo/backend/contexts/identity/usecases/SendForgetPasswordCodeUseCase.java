package com.imo.backend.contexts.identity.usecases;

import com.imo.backend.contexts.identity.RecoveryCode;
import com.imo.backend.contexts.identity.events.ForgetPasswordEvent;
import com.imo.backend.contexts.identity.repositories.RecoveryCodeRepository;
import com.imo.backend.contexts.identity.repositories.UserRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Optional;

@Service
public class SendForgetPasswordCodeUseCase {
  private final UserRepository userRepository;

  private final RecoveryCodeRepository recoveryCodeRepository;

  private final ApplicationEventPublisher applicationEventPublisher;

  private final SecureRandom secureRandom = new SecureRandom();

  private static final String RESPONSE_MESSAGE = "Se o email informado existir, você receberá uma mensagem com o código de recuperação";

  public SendForgetPasswordCodeUseCase(
      UserRepository userRepository,
      RecoveryCodeRepository recoveryCodeRepository,
      ApplicationEventPublisher applicationEventPublisher
  ) {
    this.userRepository = userRepository;
    this.recoveryCodeRepository = recoveryCodeRepository;
    this.applicationEventPublisher = applicationEventPublisher;
  }

  public String execute(String email) {
    var foundUser = this.userRepository.findByEmail(email).orElse(null);

    if (foundUser == null) {
      return RESPONSE_MESSAGE;
    }

    Optional<RecoveryCode> recoveryCode = this.recoveryCodeRepository.findByUserId(foundUser.getId());

    if (recoveryCode.isPresent()) {
      return "Espere alguns minutos para solicitar novamente";
    }

    int code = this.secureRandom.nextInt(1_000_000);
    String checkCode = String.format("%06d", code);

    this.applicationEventPublisher.publishEvent(new ForgetPasswordEvent(
        foundUser.getId(),
        email,
        checkCode
    ));

    return RESPONSE_MESSAGE;
  }
}
