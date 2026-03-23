package com.imo.backend.contexts.identity.usecases;

import com.imo.backend.contexts.identity.RecoveryCode;
import com.imo.backend.contexts.identity.User;
import com.imo.backend.contexts.identity.repositories.RecoveryCodeRepository;
import com.imo.backend.contexts.identity.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RecoveryPasswordUseCase {
  private final UserRepository userRepository;

  private final RecoveryCodeRepository recoveryCodeRepository;

  private final PasswordEncoder passwordEncoder;

  private static final String ERROR_MESSAGE = "Código inválido ou expirado";

  public RecoveryPasswordUseCase(
      UserRepository userRepository,
      RecoveryCodeRepository recoveryCodeRepository,
      PasswordEncoder passwordEncoder
  ) {
    this.userRepository = userRepository;
    this.recoveryCodeRepository = recoveryCodeRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public String execute(String email, String newPassword, String code) {
    User foundUser = this.userRepository.findByEmail(email).orElse(null);

    if (foundUser == null) {
      return ERROR_MESSAGE;
    }

    RecoveryCode foundRecoveryCode = this.recoveryCodeRepository
        .findByUserId(foundUser.getId())
        .orElse(null);

    if (foundRecoveryCode == null || foundRecoveryCode.isWasUsed() || !passwordEncoder.matches(
        code,
        foundRecoveryCode.getCode()
    )) {
      return ERROR_MESSAGE;
    }

    foundRecoveryCode.setWasUsed(true);
    this.recoveryCodeRepository.save(foundRecoveryCode);

    foundUser.setPassword(this.passwordEncoder.encode(newPassword));
    this.userRepository.save(foundUser);

    return "Senha atualizada com sucesso";
  }
}
