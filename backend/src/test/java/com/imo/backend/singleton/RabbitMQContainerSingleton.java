package com.imo.backend.singleton;

import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;

public class RabbitMQContainerSingleton {
  @Container
  private static RabbitMQContainer rabbitMQContainer;

  public static RabbitMQContainer getInstance() {
    if (rabbitMQContainer == null) {
      rabbitMQContainer = new RabbitMQContainer("rabbitmq:3-management");
    }

    return rabbitMQContainer;
  }
}
