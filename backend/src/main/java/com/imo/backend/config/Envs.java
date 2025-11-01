package com.imo.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Envs {
  @Value("${exchange_name}")
  public String EXCHANGE_NAME;

  @Value("${routing_key.confirm_email}")
  public String ROUTING_KEY_CONFIRM_EMAIL;

  @Value("${routing_key.forget_password}")
  public String ROUTING_KEY_FORGET_PASSWORD;
}
