package com.imo.backend.contexts.learning_path.skill_profile.usecases;

import com.imo.backend.contexts.catalog.skill.Skill;
import com.imo.backend.contexts.learning_path.skill_profile.SkillProfile;
import com.imo.backend.contexts.learning_path.skill_profile.events.SkillProfileUpdatedEvent;
import com.imo.backend.contexts.learning_path.skill_profile.gateways.SkillProfileDataGateway;
import com.imo.backend.contexts.learning_path.skill_profile.lib.SkillCoverageCalculator;
import com.imo.backend.contexts.learning_path.skill_profile.repositories.SkillProfileRepository;
import java.util.List;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class UpdateSkillProfileOnCourseFinishedUseCase {
  private final SkillProfileDataGateway skillProfileDataGateway;
  private final SkillProfileRepository skillProfileRepository;
  private final SkillCoverageCalculator skillCoverageCalculator;
  private final ApplicationEventPublisher applicationEventPublisher;

  public UpdateSkillProfileOnCourseFinishedUseCase(
      SkillProfileDataGateway skillProfileDataGateway,
      SkillProfileRepository skillProfileRepository,
      SkillCoverageCalculator skillCoverageCalculator,
      ApplicationEventPublisher applicationEventPublisher) {
    this.skillProfileDataGateway = skillProfileDataGateway;
    this.skillProfileRepository = skillProfileRepository;
    this.skillCoverageCalculator = skillCoverageCalculator;
    this.applicationEventPublisher = applicationEventPublisher;
  }

  public List<SkillProfile> execute(String userId, String courseId) {
    this.skillProfileDataGateway.assertUserExists(userId);
    List<Skill> courseSkills = this.skillProfileDataGateway.findSkillsByCourseIdOrThrow(courseId);

    List<SkillProfile> skillProfilesToSave =
        courseSkills.stream().map(skill -> this.upsertSkillProfile(userId, skill)).toList();

    List<SkillProfile> savedSkillProfiles =
        this.skillProfileRepository.saveAll(skillProfilesToSave);
    this.applicationEventPublisher.publishEvent(new SkillProfileUpdatedEvent(userId));

    return savedSkillProfiles;
  }

  private SkillProfile upsertSkillProfile(String userId, Skill skill) {
    SkillProfile skillProfile =
        this.skillProfileRepository
            .findByUserIdAndSkillId(userId, skill.getId())
            .orElse(new SkillProfile(userId, skill.getId(), 0));

    int nextCoverage =
        this.skillCoverageCalculator.calculateNextCoverage(
            skillProfile.getCoverage(), skill.getIsEssential());
    skillProfile.setCoverage(nextCoverage);

    return skillProfile;
  }
}
