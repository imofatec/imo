package com.imo.backend.contexts.identity.recovery.usecases;

import com.imo.backend.contexts.common.exceptions.custom.BadRequestException;
import com.imo.backend.contexts.identity.recovery.RecoveryCode;
import com.imo.backend.contexts.identity.recovery.lib.CodeGenerator;
import com.imo.backend.contexts.identity.recovery.repositories.RecoveryCodeRepository;
import com.imo.backend.contexts.identity.user.events.ForgetPasswordEvent;
import com.imo.backend.contexts.identity.user.repositories.UserRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SendForgetPasswordCodeUseCase {
  private final UserRepository userRepository;

  private final RecoveryCodeRepository recoveryCodeRepository;

  private final ApplicationEventPublisher applicationEventPublisher;

  private final PasswordEncoder passwordEncoder;

  private final CodeGenerator codeGenerator;

  private static final String RESPONSE_MESSAGE = "Se o email informado existir, você receberá uma mensagem com o código de recuperação";

  public SendForgetPasswordCodeUseCase(
      UserRepository userRepository,
      RecoveryCodeRepository recoveryCodeRepository,
      ApplicationEventPublisher applicationEventPublisher,
      PasswordEncoder passwordEncoder,
      CodeGenerator codeGenerator
  ) {
    this.userRepository = userRepository;
    this.recoveryCodeRepository = recoveryCodeRepository;
    this.applicationEventPublisher = applicationEventPublisher;
    this.passwordEncoder = passwordEncoder;
    this.codeGenerator = codeGenerator;
  }

  public String execute(String email) {
    var foundUser = this.userRepository.findByEmail(email).orElse(null);

    if (foundUser == null) {
      return RESPONSE_MESSAGE;
    }

    var existingCode = this.recoveryCodeRepository.findByUserId(foundUser.getId());

    if (existingCode.isPresent()) {
      throw new BadRequestException("Espere alguns minutos para solicitar novamente");
    }

    String rawCode = this.codeGenerator.generate();

    RecoveryCode recoveryCode = new RecoveryCode(
        foundUser.getId(),
        this.passwordEncoder.encode(rawCode)
    );
    this.recoveryCodeRepository.save(recoveryCode);

    this.applicationEventPublisher.publishEvent(new ForgetPasswordEvent(
        foundUser.getId(),
        foundUser.getName(),
        foundUser.getEmail(),
        rawCode
    ));

    return RESPONSE_MESSAGE;
  }
}
