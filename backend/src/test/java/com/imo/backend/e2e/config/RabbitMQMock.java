package com.imo.backend.e2e.config;

import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class RabbitMQMock {

  @Bean
  @Primary
  public RabbitTemplate mockRabbitTemplate() {
    return Mockito.mock(RabbitTemplate.class);
  }
}
