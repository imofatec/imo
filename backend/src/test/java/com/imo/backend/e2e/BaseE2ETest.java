package com.imo.backend.e2e;

import com.imo.backend.singleton.MongoDBContainerSingleton;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseE2ETest {
  static MongoDBContainer mongoDBContainerSingleton = MongoDBContainerSingleton.getInstance();

  @DynamicPropertySource
  static void configureProperties(DynamicPropertyRegistry registry) {
    if (!mongoDBContainerSingleton.isRunning()) {
      mongoDBContainerSingleton.start();
    }

    registry.add("spring.data.mongodb.uri", mongoDBContainerSingleton::getReplicaSetUrl);
  }

  @MockitoBean
  protected RabbitTemplate rabbitTemplate;

  @MockitoBean
  protected JavaMailSender javaMailSender;

  @LocalServerPort
  int port;

  @Autowired
  MongoTemplate mongoTemplate;

  @AfterEach
  void cleanDatabase() {
    mongoTemplate.getCollectionNames().forEach(mongoTemplate::dropCollection);
  }

  @BeforeEach
  void setUp() {
    RestAssured.baseURI = "http://localhost";
    RestAssured.port = this.port;
    RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
  }
}
