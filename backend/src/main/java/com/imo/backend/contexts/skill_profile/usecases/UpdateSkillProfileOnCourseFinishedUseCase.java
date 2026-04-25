package com.imo.backend.contexts.skill_profile.usecases;

import com.imo.backend.contexts.catalog.course.repositories.CourseRepository;
import com.imo.backend.contexts.catalog.skill.Skill;
import com.imo.backend.contexts.catalog.skill.repositories.SkillRepository;
import com.imo.backend.contexts.common.exceptions.custom.NotFoundException;
import com.imo.backend.contexts.identity.user.repositories.UserRepository;
import com.imo.backend.contexts.skill_profile.SkillProfile;
import com.imo.backend.contexts.skill_profile.lib.SkillCoverageCalculator;
import com.imo.backend.contexts.skill_profile.repositories.SkillProfileRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UpdateSkillProfileOnCourseFinishedUseCase {
  private final UserRepository userRepository;
  private final CourseRepository courseRepository;
  private final SkillRepository skillRepository;
  private final SkillProfileRepository skillProfileRepository;
  private final SkillCoverageCalculator skillCoverageCalculator;

  public UpdateSkillProfileOnCourseFinishedUseCase(
      UserRepository userRepository,
      CourseRepository courseRepository,
      SkillRepository skillRepository,
      SkillProfileRepository skillProfileRepository,
      SkillCoverageCalculator skillCoverageCalculator) {
    this.userRepository = userRepository;
    this.courseRepository = courseRepository;
    this.skillRepository = skillRepository;
    this.skillProfileRepository = skillProfileRepository;
    this.skillCoverageCalculator = skillCoverageCalculator;
  }

  public List<SkillProfile> execute(String userId, String courseId) {
    this.userRepository.findByIdOrThrow(userId);
    var course = this.courseRepository.findByIdOrThrow(courseId);

    List<String> courseSkillIds = course.getSkillIdsAsString();
    List<Skill> courseSkills = this.skillRepository.findAllById(courseSkillIds);

    if (courseSkills.size() != courseSkillIds.size()) {
      throw new NotFoundException("Uma ou mais skills do curso não foram encontradas");
    }

    List<SkillProfile> skillProfilesToSave =
        courseSkills.stream().map(skill -> this.upsertSkillProfile(userId, skill)).toList();

    return this.skillProfileRepository.saveAll(skillProfilesToSave);
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
