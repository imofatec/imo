package com.imo.backend.contexts.catalog.course;

import com.imo.backend.contexts.catalog.course.value_objects.Categories;
import com.imo.backend.contexts.catalog.course.value_objects.Category;
import com.imo.backend.contexts.catalog.course.value_objects.CourseName;
import com.imo.backend.contexts.catalog.course.value_objects.Level;
import com.imo.backend.contexts.catalog.lesson.Lesson;
import com.imo.backend.contexts.common.Entity;
import com.imo.backend.contexts.common.exceptions.custom.BadRequestException;
import com.imo.backend.contexts.common.exceptions.custom.ForbiddenException;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Document("courses")
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
    if (skillIds == null) {
      throw new BadRequestException("O curso precisa de no minimo uma skill");
    }
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
