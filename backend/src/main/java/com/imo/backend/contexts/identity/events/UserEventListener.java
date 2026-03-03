package com.imo.backend.contexts.identity.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imo.backend.config.Envs;
import com.imo.backend.contexts.apagar_dps.Outbox;
import com.imo.backend.contexts.apagar_dps.OutboxEvent;
import com.imo.backend.contexts.apagar_dps.OutboxStatus;
import com.imo.backend.contexts.apagar_dps.services.CreateOutboxService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserEventListener {
  private final Envs envs;

  private final ObjectMapper objectMapper;

  private final RabbitTemplate rabbitTemplate;

  private final CreateOutboxService<ForgetPasswordEvent> outboxService;

  public UserEventListener(
      Envs envs,
      ObjectMapper objectMapper,
      RabbitTemplate rabbitTemplate,
      CreateOutboxService<ForgetPasswordEvent> outboxService
  ) {
    this.envs = envs;
    this.objectMapper = objectMapper;
    this.rabbitTemplate = rabbitTemplate;
    this.outboxService = outboxService;
  }

  @ApplicationModuleListener
  public void handle(SendEmailConfirmationEvent event) {
    try {
      String json = this.objectMapper.writeValueAsString(event.user());
      this.rabbitTemplate.convertAndSend(
          this.envs.EXCHANGE_NAME,
          this.envs.ROUTING_KEY_CONFIRM_EMAIL,
          new Message(json.getBytes())
      );
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
  }

  @ApplicationModuleListener
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
          this.envs.EXCHANGE_NAME,
          this.envs.ROUTING_KEY_FORGET_PASSWORD,
          new Message(json.getBytes())
      );

    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
  }
}
