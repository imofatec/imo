package com.imo.backend.contexts.catalog.course;

import com.imo.backend.contexts.catalog.course.repositories.CourseRepository;
import com.imo.backend.contexts.catalog.course.value_objects.Category;
import com.imo.backend.contexts.catalog.skill.Skill;
import com.imo.backend.contexts.catalog.skill.repositories.SkillRepository;
import com.imo.backend.contexts.common.exceptions.custom.BadRequestException;
import com.imo.backend.contexts.common.exceptions.custom.ConflictException;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class CoursePolicies {

  private final CourseRepository courseRepository;
  private final SkillRepository skillRepository;

  public CoursePolicies(CourseRepository courseRepository, SkillRepository skillRepository) {
    this.courseRepository = courseRepository;
    this.skillRepository = skillRepository;
  }

  public void checkSlugConflict(String contributorId, String newSlug, String currentCourseId) {
    boolean existingContributorCourse =
        this.courseRepository.findAllByContributorId(contributorId).stream()
            .anyMatch(
                course ->
                    course.getName().slug().equals(newSlug)
                        && (currentCourseId == null || !course.getId().equals(currentCourseId)));

    if (existingContributorCourse) {
      throw new ConflictException(String.format("Curso %s já existe", newSlug));
    }
  }

  public void validateCourseSkills(Category courseCategory, List<String> skillIds) {
    List<Skill> existingSkills = this.findExistingSkillsOrThrow(skillIds);
    this.assertSkillsMatchCourseCategory(courseCategory, existingSkills);
  }

  private List<Skill> findExistingSkillsOrThrow(List<String> skillIds) {
    List<Skill> skills = this.skillRepository.findAllById(skillIds);

    if (skills.size() != skillIds.size()) {
      throw new BadRequestException("Uma ou mais skills informadas não existem");
    }

    return skills;
  }

  private void assertSkillsMatchCourseCategory(Category courseCategory, List<Skill> skills) {
    boolean hasSkillFromAnotherCategory =
        skills.stream()
            .map(Skill::getCategory)
            .anyMatch(skillCategory -> !skillCategory.equals(courseCategory));

    if (hasSkillFromAnotherCategory) {
      throw new BadRequestException(
          "Todas as skills do curso precisam pertencer à mesma categoria do curso");
    }
  }
}
