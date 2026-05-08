package com.imo.backend.contexts.catalog.course;

import com.imo.backend.contexts.catalog.lesson.Lesson;
import com.imo.backend.contexts.common.Entity;
import com.imo.backend.contexts.common.MongoDB;
import com.imo.backend.contexts.common.exceptions.custom.BadRequestException;
import com.imo.backend.contexts.common.exceptions.custom.ForbiddenException;
import java.util.List;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Document("courses")
@CompoundIndex(
    name = "uk_courses_contributor_slug",
    def = "{'contributorId': 1, 'name.slug': 1}",
    unique = true)
@Data
public class Course extends Entity {
  // relations
  private ObjectId contributorId;

  private List<ObjectId> skillIds;

  // attributes
  private boolean isActive;

  private CourseName name;

  private Level level;

  private Category category;

  private String description;

  private String firstLessonYoutubeLink;

  private int lessonsCount;

  public Course() {}

  public Course(
      boolean isActive,
      String contributorId,
      String name,
      String level,
      Categories category,
      String description,
      String firstLessonYoutubeLink,
      int lessonsCount,
      List<String> skillIds) {
    setActive(isActive);
    setName(new CourseName(name));
    setCategory(new Category(category));
    setLevel(new Level(level));
    setDescription(description);
    setFirstLessonYoutubeLink(firstLessonYoutubeLink);
    setLessonsCount(lessonsCount);
    setContributorId(contributorId);
    setSkillIds(skillIds);
  }

  public void setContributorId(String contributorId) {
    this.contributorId = new ObjectId(contributorId);
  }

  public String getContributorId() {
    return this.contributorId.toString();
  }

  public void setFirstLessonYoutubeLink(String firstLessonYoutubeLink) {
    this.firstLessonYoutubeLink = Lesson.formatYoutubeLink(firstLessonYoutubeLink);
  }

  public void setSkillIds(List<String> skillIds) {
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

    this.skillIds = skillIds.stream().map(ObjectId::new).toList();
  }

  public List<String> getSkillIdsAsString() {
    return this.skillIds.stream().map(ObjectId::toString).toList();
  }

  public void toggleStatus() {
    this.isActive = !this.isActive;
  }

  public void assertCanProgress() {
    if (!this.isActive) {
      throw new ForbiddenException("Não é possível progredir em um curso desativado");
    }
  }
}
