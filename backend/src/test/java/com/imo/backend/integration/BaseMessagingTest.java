package com.imo.backend.integration;

import com.imo.backend.contexts.notification.lib.MailManager;
import com.imo.backend.singleton.MongoDBContainerSingleton;
import com.imo.backend.singleton.RabbitMQContainerSingleton;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public abstract class BaseMessagingTest {
  static MongoDBContainer mongoDBContainer = MongoDBContainerSingleton.getInstance();
  static RabbitMQContainer rabbitMQContainer = RabbitMQContainerSingleton.getInstance();

  @DynamicPropertySource
  static void configureProperties(DynamicPropertyRegistry registry) {
    if (!mongoDBContainer.isRunning()) {
      mongoDBContainer.start();
    }
    if (!rabbitMQContainer.isRunning()) {
      rabbitMQContainer.start();
    }

    registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    registry.add("spring.rabbitmq.addresses", rabbitMQContainer::getAmqpUrl);
  }

  @MockitoBean protected JavaMailSender javaMailSender;

  @MockitoBean(name = "javaMailManager")
  protected MailManager mailManager;

  @Autowired protected MongoTemplate mongoTemplate;

  @AfterEach
  void cleanDatabase() {
    mongoTemplate.getCollectionNames().forEach(mongoTemplate::dropCollection);
  }
}
