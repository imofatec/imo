package com.imo.backend.contexts.notification;

import com.imo.backend.contexts.identity.user.events.ForgetPasswordEvent;
import com.imo.backend.contexts.notification.lib.MailManager;
import com.imo.backend.contexts.notification.lib.MailMessageBuilder;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ForgetPasswordConsumer {
  private final MailManager mailManager;

  private static final String EMAIL_SUBJECT = "IMO - Recuperar senha";
  private static final String TEMPLATE_NAME = "forget_password";

  public ForgetPasswordConsumer(@Qualifier("javaMailManager") MailManager mailManager) {
    this.mailManager = mailManager;
  }

  @RabbitListener(queues = "${rabbitmq.queue.forget_password}")
  public void handle(ForgetPasswordEvent event) {
    log.debug("FORGET_PASSWORD_EVENT: mensagem recebida {}", event.code());

    MailMessageBuilder messageBuilder =
        new MailMessageBuilder().setTo(event.email()).setSubject(EMAIL_SUBJECT);

    List<String> digits = event.code().chars().mapToObj(c -> String.valueOf((char) c)).toList();

    Map<String, Object> templateVariables = Map.of("digits", digits, "name", event.name());

    log.debug("FORGET_PASSWORD_EVENT: template configurado");

    this.mailManager.sendWithTemplate(messageBuilder, TEMPLATE_NAME, templateVariables);
  }
}
