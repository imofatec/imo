package com.imo.backend.singleton;

import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;

public class MongoDBContainerSingleton {
  @Container
  private static MongoDBContainer mongoDBContainer;

  public static MongoDBContainer getInstance() {
    if (mongoDBContainer == null) {
      mongoDBContainer = new MongoDBContainer("mongo:7.0");
    }

    return mongoDBContainer;
  }
}
