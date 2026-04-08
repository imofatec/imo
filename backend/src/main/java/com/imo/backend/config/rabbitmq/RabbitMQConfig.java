package com.imo.backend.config.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
  @Value("${rabbitmq.identity.exchange}")
  private String identityExchange;

  @Value("${rabbitmq.queue.confirm_email}")
  private String confirmEmailQueue;

  @Value("${rabbitmq.routing.confirm_email}")
  private String routingKeyConfirmEmail;

  @Value("${rabbitmq.queue.forget_password}")
  private String forgetPasswordQueue;

  @Value("${rabbitmq.routing.forget_password}")
  private String routingKeyForgetPassword;

  @Bean
  public TopicExchange topicExchange() {
    return new TopicExchange(this.identityExchange);
  }

  @Bean
  public Queue confirmEmailQueue() {
    return QueueBuilder.durable(confirmEmailQueue).build();
  }

  @Bean
  public Binding confirmEmailBinding(Queue confirmEmailQueue, TopicExchange topicExchange) {
    return BindingBuilder.bind(confirmEmailQueue).to(topicExchange).with(routingKeyConfirmEmail);
  }

  @Bean
  public Queue forgetPasswordQueue() {
    return QueueBuilder.durable(forgetPasswordQueue).build();
  }

  @Bean
  public Binding forgetPasswordBinding(Queue forgetPasswordQueue, TopicExchange topicExchange) {
    return BindingBuilder.bind(forgetPasswordQueue)
        .to(topicExchange)
        .with(routingKeyForgetPassword);
  }

  @Bean
  public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
    return new Jackson2JsonMessageConverter();
  }

  @Bean
  public RabbitTemplate rabbitTemplate(
      ConnectionFactory connectionFactory, MessageConverter messageConverter) {
    RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMessageConverter(messageConverter);
    return rabbitTemplate;
  }
}
