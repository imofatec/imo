package com.imo.backend.e2e.config.singleton;

import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;


public class SharedMongoDBContainer {

    private static final String MONGO_IMAGE = "mongo:7.0";

    private static MongoDBContainer mongoDBContainer;

    public static synchronized MongoDBContainer getInstance() {
        if (mongoDBContainer == null) {
            mongoDBContainer = new MongoDBContainer(DockerImageName.parse(MONGO_IMAGE))
                    .withReuse(true);
        }
        return mongoDBContainer;
    }

    public static void start() {
        getInstance().start();
    } 

    public static void stop() {
        if (mongoDBContainer != null) {
            mongoDBContainer.stop();
        }
    }

    public static String getReplicaSetUrl() {
        return getInstance().getReplicaSetUrl();
    }
}