package com.imo.backend.config.mongodb.populate.skills;

import com.imo.backend.contexts.catalog.skill.Skill;
import com.imo.backend.contexts.catalog.skill.repositories.SkillRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PopulateSkills {
  private final SkillRepository skillRepository;
  private final StaticSkillsCatalog staticSkillsCatalog;

  public PopulateSkills(SkillRepository skillRepository, StaticSkillsCatalog staticSkillsCatalog) {
    this.skillRepository = skillRepository;
    this.staticSkillsCatalog = staticSkillsCatalog;
  }

  public List<Skill> execute() {
    this.skillRepository.deleteAll();
    List<Skill> savedSkills = this.skillRepository.saveAll(this.staticSkillsCatalog.items());
    log.info("Coleção de skills populada com {} registros", savedSkills.size());
    return savedSkills;
  }

  public List<Skill> initializeIfEmpty() {
    if (this.skillRepository.count() > 0) {
      log.debug("Coleção de skills já inicializada, bootstrap ignorado");
      return List.of();
    }

    return this.execute();
  }
}
