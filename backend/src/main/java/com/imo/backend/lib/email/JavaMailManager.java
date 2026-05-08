package com.imo.backend.lib.email;

import com.imo.backend.contexts.notification.lib.MailManager;
import com.imo.backend.contexts.notification.lib.MailMessageBuilder;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Slf4j
@Service
public class JavaMailManager implements MailManager {
  private final JavaMailSender javaMailSender;
  private final TemplateEngine templateEngine;

  @Value("${email.username}")
  private String usernameSender;

  public JavaMailManager(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
    this.javaMailSender = javaMailSender;
    this.templateEngine = templateEngine;
  }

  @Override
  public void send(MailMessageBuilder.Message message) {
    MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();

    try {
      MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

      helper.setFrom(this.usernameSender);
      helper.setTo(message.to());
      helper.setSubject(message.subject());

      if (message.textContent() != null) {
        helper.setText(message.textContent());
      }

      helper.setText(message.htmlContent(), true);

      for (MailMessageBuilder.Attachment attachment : message.attachments()) {
        helper.addAttachment(attachment.filename(), attachment.file());
      }

      log.debug("JAVA_MAIL: enviando email com: {} para: {}", this.usernameSender, message.to());
      this.javaMailSender.send(mimeMessage);
    } catch (MessagingException e) {
      log.error("JAVA_MAIL: ENVIO DE EMAIL FALHOU {}", e.getMessage());
    }
  }

  @Override
  public void sendWithTemplate(
      MailMessageBuilder incompleteBuilder, String templateName, Map<String, Object> variables) {
    Context context = new Context();
    variables.forEach(context::setVariable);
    String html = this.templateEngine.process(templateName, context);

    incompleteBuilder.setHtmlContent(html).build();

    send(incompleteBuilder.build());
  }
}
