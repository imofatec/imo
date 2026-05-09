package com.imo.backend.unit.identity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.imo.backend.contexts.common.exceptions.custom.BadRequestException;
import com.imo.backend.contexts.identity.recovery.RecoveryCode;
import com.imo.backend.contexts.identity.recovery.RecoveryCodePolicy;
import com.imo.backend.contexts.identity.recovery.repositories.RecoveryCodeRepository;
import com.imo.backend.contexts.identity.user.User;
import com.imo.backend.contexts.identity.user.repositories.UserRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class RecoveryCodePolicyTest {

  @Mock private UserRepository userRepository;

  @Mock private RecoveryCodeRepository recoveryCodeRepository;

  @Mock private PasswordEncoder passwordEncoder;

  @InjectMocks RecoveryCodePolicy policies;

  @Test
  @DisplayName("happy path (assertCanRecovery): codigo valido")
  void shouldReturnCodeAndUserWhenCodeIsValid() {
    String userId = new ObjectId().toString();
    String email = "kissshot@email.com";
    String rawCode = "123456";
    String encodedCode = "encoded123456";

    User user = new User("Kiss Shot", email, "pass123", true);
    RecoveryCode recoveryCode = new RecoveryCode();
    recoveryCode.setUserId(userId);
    recoveryCode.setCode(encodedCode);
    recoveryCode.setWasUsed(false);
    recoveryCode.setExpiresAt(LocalDateTime.now().plusMinutes(5));

    when(this.userRepository.findByEmail(email)).thenReturn(Optional.of(user));
    user.setId(userId);
    when(this.recoveryCodeRepository.findValidByUserId(userId))
        .thenReturn(Optional.of(recoveryCode));
    when(this.passwordEncoder.matches(rawCode, encodedCode)).thenReturn(true);

    RecoveryCodePolicy.RecoveryCodeAndUser result = this.policies.assertCanRecovery(rawCode, email);

    assertEquals(recoveryCode, result.recoveryCode());
    assertEquals(user, result.user());

    verify(this.userRepository).findByEmail(email);
    verify(this.recoveryCodeRepository).findValidByUserId(userId);
    verify(this.passwordEncoder).matches(rawCode, encodedCode);
  }

  @Test
  @DisplayName("exception (assertCanRecovery): codigo inexistente, expirado ou ja utilizado")
  void shouldThrowBadRequestWhenRecoveryCodeIsNotActive() {
    String userId = new ObjectId().toString();
    String email = "kissshot@email.com";
    String rawCode = "123456";

    User user = new User("Kiss Shot", email, "pass123", true);
    when(this.userRepository.findByEmail(email)).thenReturn(Optional.of(user));
    user.setId(userId);
    when(this.recoveryCodeRepository.findValidByUserId(userId)).thenReturn(Optional.empty());

    assertThrows(BadRequestException.class, () -> this.policies.assertCanRecovery(rawCode, email));

    verify(this.userRepository).findByEmail(email);
    verify(this.recoveryCodeRepository).findValidByUserId(userId);
  }
}
