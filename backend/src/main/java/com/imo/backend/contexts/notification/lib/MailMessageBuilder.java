package com.imo.backend.contexts.notification.lib;

import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class MailMessageBuilder {
  private String to;
  private String subject;
  private String textContent;
  private String htmlContent;
  private final List<Attachment> attachments = new ArrayList<>();

  public MailMessageBuilder setTo(String to) {
    this.to = to;
    return this;
  }

  public MailMessageBuilder setSubject(String subject) {
    this.subject = subject;
    return this;
  }

  public MailMessageBuilder setTextContent(String textContent) {
    this.textContent = textContent;
    return this;
  }

  public MailMessageBuilder setHtmlContent(String htmlContent) {
    this.htmlContent = htmlContent;
    return this;
  }

  public MailMessageBuilder addAttachment(String filename, File file) {
    this.attachments.add(new Attachment(filename, file));
    return this;
  }

  public Message build() {
    return new Message(to, subject, textContent, htmlContent, List.copyOf(attachments));
  }

  public void clear() {
    this.to = null;
    this.subject = null;
    this.textContent = null;
    this.htmlContent = null;
    this.attachments.clear();
  }

  public record Message(
      String to,
      String subject,
      String textContent,
      String htmlContent,
      List<Attachment> attachments
  ) {
  }

  public record Attachment(
      String filename,
      File file
  ) {
  }
}
