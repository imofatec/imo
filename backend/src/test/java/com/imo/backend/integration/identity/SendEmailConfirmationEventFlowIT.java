package com.imo.backend.integration.identity;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

import com.imo.backend.contexts.identity.user.events.SendEmailConfirmationEvent;
import com.imo.backend.contexts.notification.lib.MailManager;
import com.imo.backend.contexts.notification.lib.MailMessageBuilder;
import com.imo.backend.integration.BaseMessagingTest;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

class SendEmailConfirmationEventFlowIT extends BaseMessagingTest {

  @Autowired private ApplicationEventPublisher applicationEventPublisher;

  @Autowired private MailManager mailManager;

  @Test
  @DisplayName(
      "happy path: (fluxo completo): publicar SendEmailConfirmationEvent e verificar envio de email")
  void shouldSendEmailWhenSendEmailConfirmationEventIsPublished() {
    String name = "Nome teste";
    String email = "email@email.com";

    SendEmailConfirmationEvent event = new SendEmailConfirmationEvent(email, name);

    applicationEventPublisher.publishEvent(event);

    verify(mailManager, timeout(10_000))
        .sendWithTemplate(
            any(MailMessageBuilder.class),
            eq("confirmation_email"),
            eq(
                Map.of(
                    "name",
                    name,
                    "confirmationURL",
                    "http://localhost:5173/user/confirmar-email")));
  }
}
