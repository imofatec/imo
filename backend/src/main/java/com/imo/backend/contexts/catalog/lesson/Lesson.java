package com.imo.backend.contexts.catalog.lesson;

import com.imo.backend.contexts.common.Entity;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("lessons")
@EqualsAndHashCode(callSuper = true)
@CompoundIndexes({
  @CompoundIndex(
      name = "uk_lessons_course_index",
      def = "{'courseId': 1, 'indexInCourse': 1}",
      unique = true),
  @CompoundIndex(
      name = "uk_lessons_course_title",
      def = "{'courseId': 1, 'title': 1}",
      unique = true),
  @CompoundIndex(
      name = "uk_lessons_course_youtube_link",
      def = "{'courseId': 1, 'youtubeLink': 1}",
      unique = true)
})
@Data
public class Lesson extends Entity {
  // relations
  private ObjectId courseId;

  private int indexInCourse;

  private String title;

  private String description;

  private String youtubeLink;

  public Lesson() {}

  public Lesson(
      String courseId, int indexInCourse, String title, String description, String youtubeLink) {
    setCourseId(courseId);
    setIndexInCourse(indexInCourse);
    setTitle(title);
    setDescription(description);
    setYoutubeLink(youtubeLink);
  }

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
    IntStream.range(0, lessons.size())
        .forEach(
            i -> {
              lessons.get(i).setIndexInCourse(i + 1);
            });

    return lessons;
  }

  public static void sortLessonsByIndexInCourse(List<Lesson> lessons) {
    lessons.sort(Comparator.comparing(Lesson::getIndexInCourse));
  }
}
