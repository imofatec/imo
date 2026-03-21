package com.imo.backend.contexts.identity.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imo.backend.contexts.apagar_dps.Outbox;
import com.imo.backend.contexts.apagar_dps.OutboxEvent;
import com.imo.backend.contexts.apagar_dps.OutboxStatus;
import com.imo.backend.contexts.apagar_dps.services.CreateOutboxService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserEventListener {
  private final ObjectMapper objectMapper;

  private final RabbitTemplate rabbitTemplate;

  private final CreateOutboxService<ForgetPasswordEvent> outboxService;

  @Value("${rabbitmq.identity.exchange}")
  private String identityExchangeName;

  @Value("${rabbitmq.routing.confirm_email}")
  private String routingKeyConfirmEmail;

  @Value("${rabbitmq.routing.forget_password}")
  private String routingKeyForgetPassword;

  public UserEventListener(
      ObjectMapper objectMapper,
      RabbitTemplate rabbitTemplate,
      CreateOutboxService<ForgetPasswordEvent> outboxService
  ) {
    this.objectMapper = objectMapper;
    this.rabbitTemplate = rabbitTemplate;
    this.outboxService = outboxService;
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
    try {
      var newOutbox = this.outboxService.execute(new Outbox<>(
          event,
          OutboxStatus.PENDING,
          OutboxEvent.USER_FORGET_PASSWORD
      ));

      var payload = new ForgetPasswordMessagePayload(
          newOutbox.getId(),
          event.userId(),
          event.email(),
          event.code()
      );

      String json = this.objectMapper.writeValueAsString(payload);

      this.rabbitTemplate.convertAndSend(
          this.identityExchangeName,
          this.routingKeyForgetPassword,
          new Message(json.getBytes())
      );

    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
  }
}
