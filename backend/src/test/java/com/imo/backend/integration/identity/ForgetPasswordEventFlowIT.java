package com.imo.backend.integration.identity;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

import com.imo.backend.contexts.identity.user.events.ForgetPasswordEvent;
import com.imo.backend.contexts.notification.lib.MailManager;
import com.imo.backend.contexts.notification.lib.MailMessageBuilder;
import com.imo.backend.integration.BaseMessagingTest;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

class ForgetPasswordEventFlowIT extends BaseMessagingTest {

  @Autowired private ApplicationEventPublisher applicationEventPublisher;

  @Autowired private MailManager mailManager;

  @Test
  @DisplayName(
      "happy path: (fluxo completo): publicar ForgetPasswordEvent e verificar envio de email")
  void shouldSendEmailWhenForgetPasswordEventIsPublished() {
    String userId = "user123";
    String name = "Nome teste";
    String email = "email@email.com";
    String code = "123456";

    ForgetPasswordEvent event = new ForgetPasswordEvent(userId, name, email, code);

    applicationEventPublisher.publishEvent(event);

    verify(mailManager, timeout(10_000))
        .sendWithTemplate(
            any(MailMessageBuilder.class),
            eq("forget_password"),
            eq(Map.of("digits", List.of("1", "2", "3", "4", "5", "6"), "name", name)));
  }
}
