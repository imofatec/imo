package com.imo.backend.config.mongodb.populate.skills;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile({"dev", "test"})
public class SkillCatalogInitializer implements ApplicationRunner {
  private final PopulateSkills populateSkills;

  public SkillCatalogInitializer(PopulateSkills populateSkills) {
    this.populateSkills = populateSkills;
  }

  @Override
  public void run(ApplicationArguments args) {
    log.debug("Verificando catálogo base de skills");
    this.populateSkills.initializeIfEmpty();
  }
}
