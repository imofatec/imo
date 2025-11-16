package com.imo.backend.config.rabbitmq;

import com.imo.backend.config.Envs;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
  private final Envs envs;

  public RabbitMQConfig(Envs envs) {
    this.envs = envs;
  }

  @Bean
  public TopicExchange topicExchange() {
    return new TopicExchange(this.envs.EXCHANGE_NAME);
  }
}
