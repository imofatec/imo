package com.imo.backend.contexts.notification.lib;

import java.util.Map;

public interface MailManager {
  void send(MailMessageBuilder.Message message);

  void sendWithTemplate(
      MailMessageBuilder incompleteBuilder,
      String templateName,
      Map<String, Object> variables
  );
}
