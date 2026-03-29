package com.imo.backend.unit.identity;

import com.imo.backend.contexts.common.exceptions.custom.BadRequestException;
import com.imo.backend.contexts.common.exceptions.custom.ConflictException;
import com.imo.backend.contexts.identity.user.User;
import com.imo.backend.contexts.identity.user.UserPolicies;
import com.imo.backend.contexts.identity.user.commands.CreateUserCommand;
import com.imo.backend.contexts.identity.user.repositories.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserPolicyTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  @InjectMocks
  UserPolicies policies;

  private final String baseName = "Kiss Shot";

  private final String baseEmail = "kissshot@email.com";

  @Test
  @DisplayName("happy path (assertCanRegister): cadastro com email único e senhas iguais")
  void shouldAllowRegistrationWithUniqueEmailAndMatchingPasswords() {
    CreateUserCommand cmd = new CreateUserCommand(baseName, baseEmail, "123456", "123456");

    when(this.userRepository.findByEmail(baseEmail)).thenReturn(Optional.empty());

    assertDoesNotThrow(() -> this.policies.assertCanRegister(cmd));

    verify(this.userRepository).findByEmail(baseEmail);
  }

  @Test
  @DisplayName("exception (assertCanRegister): email ja existente")
  void shouldThrowConflictWhenEmailAlreadyExists() {
    CreateUserCommand cmd = new CreateUserCommand(baseName, baseEmail, "123456", "123456");
    User existingUser = new User(baseName, baseEmail, "encoded123", true);

    when(this.userRepository.findByEmail(baseEmail)).thenReturn(Optional.of(existingUser));

    assertThrows(ConflictException.class, () -> this.policies.assertCanRegister(cmd));

    verify(this.userRepository).findByEmail(baseEmail);
  }

  @Test
  @DisplayName("exception (assertCanRegister): senhas nao coincidem")
  void shouldThrowBadRequestWhenPasswordsDoNotMatch() {
    CreateUserCommand cmd = new CreateUserCommand(baseName, baseEmail, "123456", "654321");

    when(this.userRepository.findByEmail(baseEmail)).thenReturn(Optional.empty());

    assertThrows(BadRequestException.class, () -> this.policies.assertCanRegister(cmd));

    verify(this.userRepository).findByEmail(baseEmail);
  }

  @Test
  @DisplayName("happy path (assertCredentials): credenciais validas")
  void shouldAssertCredentialsWhenValid() {
    User userInPersistence = new User(baseName, baseEmail, "encoded123", true);

    when(this.userRepository.findByEmail(baseEmail)).thenReturn(Optional.of(userInPersistence));
    when(this.passwordEncoder.matches("123456", "encoded123")).thenReturn(true);

    User result = this.policies.assertCredentials(baseEmail, "123456");

    assertEquals(userInPersistence, result);

    verify(this.userRepository).findByEmail(baseEmail);
    verify(this.passwordEncoder).matches("123456", "encoded123");
  }

  @Test
  @DisplayName("exception (assertCredentials): usuário nao encontrado")
  void shouldThrowBadRequestWhenUserNotFound() {
    when(this.userRepository.findByEmail(baseEmail)).thenReturn(Optional.empty());

    assertThrows(
        BadRequestException.class,
        () -> this.policies.assertCredentials(baseEmail, "123456")
    );

    verify(this.userRepository).findByEmail(baseEmail);
  }

  @Test
  @DisplayName("exception (assertCredentials): senha incorreta")
  void shouldThrowBadRequestWhenPasswordDoesNotMatch() {
    User userInPersistence = new User(baseName, baseEmail, "encoded123", true);

    when(this.userRepository.findByEmail(baseEmail)).thenReturn(Optional.of(userInPersistence));
    when(this.passwordEncoder.matches("senhaErrada", "encoded123")).thenReturn(false);

    assertThrows(
        BadRequestException.class,
        () -> this.policies.assertCredentials(baseEmail, "senhaErrada")
    );

    verify(this.userRepository).findByEmail(baseEmail);
    verify(this.passwordEncoder).matches("senhaErrada", "encoded123");
  }

  @Test
  @DisplayName("exception (assertCredentials): senha nula")
  void shouldThrowBadRequestWhenPasswordIsNull() {
    User userInPersistence = new User(baseName, baseEmail, "encoded123", true);

    when(this.userRepository.findByEmail(baseEmail)).thenReturn(Optional.of(userInPersistence));
    when(this.passwordEncoder.matches(null, "encoded123")).thenReturn(false);

    assertThrows(BadRequestException.class, () -> this.policies.assertCredentials(baseEmail, null));

    verify(this.userRepository).findByEmail(baseEmail);
    verify(this.passwordEncoder).matches(null, "encoded123");
  }
}
