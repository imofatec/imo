package com.imo.backend.config.mongodb.populate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Component
public class Populate implements CommandLineRunner {
  private final PopulateUsers populateUsers;

  public Populate(PopulateUsers populateUsers) {
    this.populateUsers = populateUsers;
  }

  @Override
  public void run(String... args) throws Exception {

    if (!Arrays.asList(args).contains("--seed=true")) {
      return;
    }

    this.populateUsers.execute(10);

  }
}
