package com.imo.backend.contexts.catalog.lesson;

import com.imo.backend.contexts.catalog.lesson.actions.commands.UpdateLessonCommand;
import com.imo.backend.contexts.common.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

@Document("lessons")
@EqualsAndHashCode(callSuper = true)
@Data
public class Lesson extends Entity {
  //  relations
  private ObjectId courseId;

  private int indexInCourse;

  private String title;

  private String description;

  private String youtubeLink;

  public void setCourseId(String courseId) {
    this.courseId = new ObjectId(courseId);
  }

  public String getCourseId() {
    return this.courseId.toString();
  }

  public void setYoutubeLink(String youtubeLink) {
    int left = youtubeLink.indexOf("=");
    int right = youtubeLink.indexOf("&");

    if (right != -1) {
      this.youtubeLink = youtubeLink.substring(left + 1, right);
      return;
    }

    if (left != -1) {
      this.youtubeLink = youtubeLink.substring(left + 1);
      return;
    }

    this.youtubeLink = youtubeLink;
  }

  public static String formatYoutubeLink(String youtubeLink) {
    int left = youtubeLink.indexOf("=");
    int right = youtubeLink.indexOf("&");

    if (right != -1) {
      return youtubeLink.substring(left + 1, right);

    }

    if (left != -1) {
      return youtubeLink.substring(left + 1);

    }

    return youtubeLink;
  }

  public static List<Lesson> reindexLessons(List<Lesson> lessons) {
    Lesson.sortLessonsByIndexInCourse(lessons);
    IntStream.range(0, lessons.size()).forEach(i -> {
      lessons.get(i).setIndexInCourse(i + 1);
    });

    return lessons;
  }

  public static void sortLessonsByIndexInCourse(List<Lesson> lessons) {
    lessons.sort(Comparator.comparing(Lesson::getIndexInCourse));
  }

  public static void applyUpdate(Lesson lesson, UpdateLessonCommand command) {
    if (command.title() != null) {
      lesson.setTitle(command.title());
    }

    if (command.youtubeLink() != null) {
      lesson.setYoutubeLink(command.youtubeLink());
    }

    if (command.description() != null) {
      lesson.setDescription(command.description());
    }
  }
}
