package com.imo.backend.contexts.catalog.course;

import com.imo.backend.contexts.catalog.course.commands.UpdateCourseByIdCommand;
import com.imo.backend.contexts.catalog.course.value_objects.Category;
import com.imo.backend.contexts.catalog.course.value_objects.CourseName;
import com.imo.backend.contexts.catalog.course.value_objects.Level;
import com.imo.backend.contexts.catalog.lesson.Lesson;
import com.imo.backend.contexts.common.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Document("courses")
@Data
public class Course extends Entity {
  //  relations
  private ObjectId contributorId;

  //  attributes
  private boolean isActive;

  private CourseName name;

  private Level level;

  private Category category;

  private String description;

  private String firstLessonYoutubeLink;

  private int lessonsCount;

  public Course() {
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

  public static void applyUpdate(Course course, UpdateCourseByIdCommand command) {
    if (command.name() != null) {
      course.setName(new CourseName(command.name()));
    }

    if (command.category() != null) {
      course.setCategory(new Category(command.category()));
    }

    if (command.level() != null) {
      course.setLevel(new Level(command.level()));
    }

    if (command.description() != null) {
      course.setDescription(command.description());
    }

    if (command.lessonsCount() != null) {
      course.setLessonsCount(command.lessonsCount());
    }

    if (command.firstLessonYoutubeLink() != null) {
      course.setFirstLessonYoutubeLink(command.firstLessonYoutubeLink());
    }
  }

  public void toggleStatus() {
    this.isActive = !this.isActive;
  }
}
