package com.imo.backend.contexts.notification;

import com.imo.backend.contexts.identity.user.events.SendEmailConfirmationEvent;
import com.imo.backend.contexts.notification.lib.MailManager;
import com.imo.backend.contexts.notification.lib.MailMessageBuilder;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ConfirmEmailConsumer {
  private final MailManager mailManager;

  private static final String EMAIL_SUBJECT = "IMO - Confirme sua conta";
  private static final String TEMPLATE_NAME = "confirmation_email";

  @Value("${frontend.email.confirmation.url}")
  private String emailConfirmUrl;

  public ConfirmEmailConsumer(@Qualifier("javaMailManager") MailManager mailManager) {
    this.mailManager = mailManager;
  }

  @RabbitListener(queues = "${rabbitmq.queue.confirm_email}")
  public void handle(SendEmailConfirmationEvent event) {
    log.debug("SEND_EMAIL_CONFIRMATION_EVENT: mensagem recebida {}", event.email());

    MailMessageBuilder messageBuilder =
        new MailMessageBuilder().setTo(event.email()).setSubject(EMAIL_SUBJECT);

    Map<String, Object> templateVariables =
        Map.of("name", event.name(), "confirmationURL", emailConfirmUrl);

    log.debug("SEND_EMAIL_CONFIRMATION_EVENT: template configurado");

    this.mailManager.sendWithTemplate(messageBuilder, TEMPLATE_NAME, templateVariables);
  }
}
