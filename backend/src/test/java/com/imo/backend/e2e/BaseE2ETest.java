package com.imo.backend.e2e;

import com.imo.backend.config.mongodb.populate.skills.PopulateSkills;
import com.imo.backend.contexts.catalog.skill.repositories.SkillRepository;
import com.imo.backend.e2e.catalog.helpers.CatalogSkillTestHelper;
import com.imo.backend.singleton.MongoDBContainerSingleton;
import com.imo.backend.singleton.RabbitMQContainerSingleton;
import io.restassured.RestAssured;
import org.bson.Document;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.mail.javamail.JavaMailSender;
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
public abstract class BaseE2ETest {
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

  @MockitoBean protected RabbitTemplate rabbitTemplate;

  @MockitoBean protected JavaMailSender javaMailSender;

  @LocalServerPort int port;

  @Autowired MongoTemplate mongoTemplate;
  @Autowired PopulateSkills populateSkills;
  @Autowired SkillRepository skillRepository;

  @AfterEach
  void cleanDatabase() {
    mongoTemplate
        .getCollectionNames()
        .forEach(
            collectionName -> {
              if (collectionName.startsWith("system.")) {
                return;
              }

              mongoTemplate.getCollection(collectionName).deleteMany(new Document());
            });
    CatalogSkillTestHelper.clear();
  }

  @BeforeEach
  void setUp() {
    RestAssured.baseURI = "http://localhost";
    RestAssured.port = this.port;
    RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    this.populateSkills.initializeIfEmpty();
    CatalogSkillTestHelper.initialize(this.skillRepository.findAll());
  }
}
