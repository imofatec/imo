package com.imo.backend.config.mongodb.populate;

import com.imo.backend.contexts.identity.user.User;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Populate implements CommandLineRunner {
  private final MongoDatabaseFactory mongoDatabaseFactory;
  private final PopulateUsers populateUsers;
  private final PopulateCourses populateCourses;
  private final PopulateProgress populateProgress;

  public Populate(
      MongoDatabaseFactory mongoDatabaseFactory,
      PopulateUsers populateUsers,
      PopulateCourses populateCourses,
      PopulateProgress populateProgress) {
    this.mongoDatabaseFactory = mongoDatabaseFactory;
    this.populateUsers = populateUsers;
    this.populateCourses = populateCourses;
    this.populateProgress = populateProgress;
  }

  @Override
  @Profile({"dev", "test"})
  public void run(String... args) throws Exception {
    Map<String, String> argMap = parseArgs(args);

    boolean shouldSeed = Boolean.parseBoolean(argMap.getOrDefault("seed", "false"));
    int userQty = Integer.parseInt(argMap.getOrDefault("users", "100"));
    int courseQty = Integer.parseInt(argMap.getOrDefault("courses", "15"));

    if (!shouldSeed) {
      log.info("Seed flag não informada, populador não executado");
      return;
    }

    log.info("Populando banco com %d usuários e %d cursos...%n", userQty, courseQty);

    this.mongoDatabaseFactory.getMongoDatabase().drop();

    User adminUser = this.populateUsers.execute(userQty);
    this.populateCourses.execute(adminUser.getId(), courseQty);
    this.populateProgress.execute();
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
