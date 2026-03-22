package com.imo.backend.contexts.identity.events;

import com.imo.backend.contexts.identity.RecoveryCode;
import com.imo.backend.contexts.identity.repositories.RecoveryCodeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserEventListener {
  private final RabbitTemplate rabbitTemplate;

  private final RecoveryCodeRepository recoveryCodeRepository;

  private final PasswordEncoder passwordEncoder;


  @Value("${rabbitmq.identity.exchange}")
  private String identityExchangeName;

  @Value("${rabbitmq.routing.confirm_email}")
  private String routingKeyConfirmEmail;

  @Value("${rabbitmq.routing.forget_password}")
  private String routingKeyForgetPassword;

  public UserEventListener(
      RabbitTemplate rabbitTemplate,
      RecoveryCodeRepository recoveryCodeRepository,
      PasswordEncoder passwordEncoder
  ) {
    this.recoveryCodeRepository = recoveryCodeRepository;
    this.rabbitTemplate = rabbitTemplate;
    this.passwordEncoder = passwordEncoder;
  }

  @Async
  @EventListener
  public void handle(SendEmailConfirmationEvent event) {
    log.debug(
        "SEND_EMAIL_CONFIRMATION_EVENT: enviando mensagem pro broker {}",
        event.user().email()
    );
    this.rabbitTemplate.convertAndSend(
        this.identityExchangeName,
        this.routingKeyConfirmEmail,
        event.user()
    );
  }

  @Async
  @EventListener
  public void handle(ForgetPasswordEvent event) {
    log.debug(
        "FORGET_PASSWORD_EVENT: enviando mensagem pro broker {} {}",
        event.email(),
        event.code()
    );

    try {
      RecoveryCode recoveryCode = new RecoveryCode(
          event.userId(),
          this.passwordEncoder.encode(event.code())
      );
      this.recoveryCodeRepository.save(recoveryCode);

      this.rabbitTemplate.convertAndSend(
          this.identityExchangeName,
          this.routingKeyForgetPassword,
          event
      );
    } catch (Exception e) {
      log.error("FORGET_PASSWORD_EVENT: erro durante criação do recovery code {}", e.getMessage());
    }
  }
}
