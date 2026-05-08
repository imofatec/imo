package com.imo.backend.contexts.identity.recovery;

import com.imo.backend.contexts.common.exceptions.custom.BadRequestException;
import com.imo.backend.contexts.identity.recovery.repositories.RecoveryCodeRepository;
import com.imo.backend.contexts.identity.user.User;
import com.imo.backend.contexts.identity.user.repositories.UserRepository;
import java.time.LocalDateTime;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RecoveryCodePolicy {
  private final PasswordEncoder passwordEncoder;

  private final UserRepository userRepository;

  private final RecoveryCodeRepository recoveryCodeRepository;

  private static final String ERROR_MESSAGE = "Código inválido ou expirado";

  public RecoveryCodePolicy(
      PasswordEncoder passwordEncoder,
      UserRepository userRepository,
      RecoveryCodeRepository recoveryCodeRepository) {
    this.passwordEncoder = passwordEncoder;
    this.userRepository = userRepository;
    this.recoveryCodeRepository = recoveryCodeRepository;
  }

  public RecoveryCodeAndUser assertCanRecovery(String code, String userEmail) {
    User foundUser =
        this.userRepository
            .findByEmail(userEmail)
            .orElseThrow(() -> new BadRequestException(ERROR_MESSAGE));

    RecoveryCode foundRecoveryCode =
        this.recoveryCodeRepository.findValidByUserId(foundUser.getId()).orElse(null);

    if (foundRecoveryCode == null
        || foundRecoveryCode.isWasUsed()
        || foundRecoveryCode.getExpiresAt().isBefore(LocalDateTime.now())
        || !passwordEncoder.matches(code, foundRecoveryCode.getCode())) {
      throw new BadRequestException(ERROR_MESSAGE);
    }

    return new RecoveryCodeAndUser(foundRecoveryCode, foundUser);
  }

  public record RecoveryCodeAndUser(RecoveryCode recoveryCode, User user) {}
}
