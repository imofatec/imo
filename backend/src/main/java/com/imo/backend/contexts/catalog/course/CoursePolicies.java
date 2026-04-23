package com.imo.backend.contexts.catalog.course;

import com.imo.backend.contexts.catalog.course.repositories.CourseRepository;
import com.imo.backend.contexts.catalog.course.value_objects.Categories;
import com.imo.backend.contexts.catalog.course.value_objects.Category;
import com.imo.backend.contexts.catalog.skill.Skill;
import com.imo.backend.contexts.catalog.skill.repositories.SkillRepository;
import com.imo.backend.contexts.common.MongoDB;
import com.imo.backend.contexts.common.exceptions.custom.BadRequestException;
import com.imo.backend.contexts.common.exceptions.custom.ConflictException;
import java.util.List;
import java.util.Set;
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

  public void validateCourseSkills(Categories courseCategory, List<String> skillIds) {
    if (skillIds == null || skillIds.isEmpty()) {
      throw new BadRequestException("O curso deve possuir ao menos 1 skill");
    }

    if (skillIds.size() > 2) {
      throw new BadRequestException("Um curso não pode ter mais do que 2 skills");
    }

    if (Set.copyOf(skillIds).size() != skillIds.size()) {
      throw new BadRequestException("O curso não pode ter skills duplicadas");
    }

    skillIds.forEach(MongoDB::validateObjectId);

    List<Skill> skills = this.skillRepository.findAllById(skillIds);
    if (skills.size() != skillIds.size()) {
      throw new BadRequestException("Uma ou mais skills informadas não existem");
    }

    Category expectedCategory = new Category(courseCategory);
    boolean hasSkillFromAnotherCategory =
        skills.stream()
            .map(Skill::getCategory)
            .anyMatch(skillCategory -> !skillCategory.equals(expectedCategory));

    if (hasSkillFromAnotherCategory) {
      throw new BadRequestException(
          "Todas as skills do curso precisam pertencer à mesma categoria do curso");
    }
  }
}
