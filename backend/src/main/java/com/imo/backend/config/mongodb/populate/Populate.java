package com.imo.backend.config.mongodb.populate;

import com.imo.backend.config.mongodb.populate.skills.PopulateSkills;
import com.imo.backend.contexts.identity.user.User;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile({"dev", "test"})
public class Populate implements CommandLineRunner {
  private final MongoDatabaseFactory mongoDatabaseFactory;
  private final PopulateSkills populateSkills;
  private final PopulateUsers populateUsers;
  private final PopulateCourses populateCourses;
  private final PopulateProgress populateProgress;

  public Populate(
      MongoDatabaseFactory mongoDatabaseFactory,
      PopulateSkills populateSkills,
      PopulateUsers populateUsers,
      PopulateCourses populateCourses,
      PopulateProgress populateProgress) {
    this.mongoDatabaseFactory = mongoDatabaseFactory;
    this.populateSkills = populateSkills;
    this.populateUsers = populateUsers;
    this.populateCourses = populateCourses;
    this.populateProgress = populateProgress;
  }

  @Override
  public void run(String... args) throws Exception {
    Map<String, String> argMap = parseArgs(args);

    boolean shouldSeed = Boolean.parseBoolean(argMap.getOrDefault("seed", "false"));
    int userQty = Integer.parseInt(argMap.getOrDefault("users", "100"));
    int courseQty = Integer.parseInt(argMap.getOrDefault("courses", "15"));

    if (!shouldSeed) {
      log.info("Seed flag não informada, populador não executado");
      return;
    }

    log.info("Populando banco com {} usuários e {} cursos...", userQty, courseQty);

    this.clearCollections();

    this.populateSkills.execute();
    User adminUser = this.populateUsers.execute(userQty);
    this.populateCourses.execute(adminUser.getId(), courseQty);
    this.populateProgress.execute();
  }

  private void clearCollections() {
    var database = this.mongoDatabaseFactory.getMongoDatabase();

    for (String collectionName : database.listCollectionNames()) {
      if (collectionName.startsWith("system.")) {
        continue;
      }

      database.getCollection(collectionName).deleteMany(new Document());
    }
  }

  private Map<String, String> parseArgs(String... args) {
    Map<String, String> map = new HashMap<>();
    Arrays.stream(args)
        .filter(arg -> arg.startsWith("--"))
        .forEach(
            arg -> {
              String[] split = arg.substring(2).split("=", 2);
              if (split.length == 2) {
                map.put(split[0], split[1]);
              } else {
                map.put(split[0], "true");
              }
            });
    return map;
  }
}
