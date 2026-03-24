package com.imo.backend.contexts.identity.recovery.usecases;

import com.imo.backend.contexts.identity.recovery.RecoveryCode;
import com.imo.backend.contexts.identity.recovery.RecoveryCodePolicy;
import com.imo.backend.contexts.identity.recovery.repositories.RecoveryCodeRepository;
import com.imo.backend.contexts.identity.user.User;
import com.imo.backend.contexts.identity.user.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RecoveryPasswordUseCase {
  private final UserRepository userRepository;

  private final RecoveryCodeRepository recoveryCodeRepository;

  private final PasswordEncoder passwordEncoder;

  private final RecoveryCodePolicy policy;

  public RecoveryPasswordUseCase(
      UserRepository userRepository,
      RecoveryCodeRepository recoveryCodeRepository,
      PasswordEncoder passwordEncoder,
      RecoveryCodePolicy policy
  ) {
    this.userRepository = userRepository;
    this.recoveryCodeRepository = recoveryCodeRepository;
    this.passwordEncoder = passwordEncoder;
    this.policy = policy;
  }

  public String execute(String email, String newPassword, String code) {
    RecoveryCodePolicy.RecoveryCodeAndUser recoveryCodeAndUser = this.policy.assertCanRecovery(
        code,
        email
    );
    RecoveryCode foundRecoveryCode = recoveryCodeAndUser.recoveryCode();
    User foundUser = recoveryCodeAndUser.user();

    foundRecoveryCode.setWasUsed(true);
    this.recoveryCodeRepository.save(foundRecoveryCode);

    foundUser.setPassword(this.passwordEncoder.encode(newPassword));
    this.userRepository.save(foundUser);

    return "Senha atualizada com sucesso";
  }
}
