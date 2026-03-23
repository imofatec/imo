package com.imo.backend.e2e.config;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.imo.backend.e2e.config.singleton.SharedMongoDBContainer;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ActiveProfiles("test")
@Import({RabbitMQMock.class, JavaMailSenderMock.class})
public abstract class BaseE2ETest {

  @LocalServerPort
  protected int port;

  static {
    SharedMongoDBContainer.start();
  }

  @DynamicPropertySource
  static void configureProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.data.mongodb.uri", SharedMongoDBContainer::getReplicaSetUrl);
  }

  @BeforeEach
  void setUp() {
    RestAssured.port = port;
    RestAssured.basePath = "/api";
    RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
  }

  public static RequestSpecification givenBaseRequest() {
    return given().contentType("application/json").accept("application/json");
  }
}