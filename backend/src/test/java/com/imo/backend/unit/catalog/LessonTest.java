package com.imo.backend.unit.catalog;

import com.imo.backend.contexts.catalog.lesson.Lesson;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LessonTest {

  @Test
  @DisplayName("happy path (construtor): normalizar youtubeLink da aula")
  void shouldNormalizeYoutubeLinkOnConstructor() {
    Lesson lesson = new Lesson(
        new ObjectId().toString(),
        1,
        "Aula 1",
        "Descrição 1",
        "https://www.youtube.com/watch?v=def456XYZ89&pp=test"
    );

    assertEquals("def456XYZ89", lesson.getYoutubeLink());
  }

  @Test
  @DisplayName("happy path (setYoutubeLink): normalizar youtubeLink ao atualizar")
  void shouldNormalizeYoutubeLinkWhenUpdating() {
    Lesson lesson = new Lesson(
        new ObjectId().toString(),
        1,
        "Aula 1",
        "Descrição 1",
        "https://www.youtube.com/watch?v=def456XYZ89"
    );

    lesson.setYoutubeLink("https://www.youtube.com/watch?v=ghi789XYZ89&list=OLAK5uy_nnKPUsEOrSbpD4JRcSyi68p77fxhiTUJk&index=2");

    assertEquals("ghi789XYZ89", lesson.getYoutubeLink());
  }

  @Test
  @DisplayName("happy path (reindexLessons): reordenar índices em sequência contínua")
  void shouldReindexLessonsInContinuousOrder() {
    String courseId = new ObjectId().toString();
    List<Lesson> lessons = new ArrayList<>(List.of(
        new Lesson(courseId, 3, "Aula 3", "Descrição 3", "https://www.youtube.com/watch?v=ghi789XYZ89"),
        new Lesson(courseId, 1, "Aula 1", "Descrição 1", "https://www.youtube.com/watch?v=abc123XYZ89"),
        new Lesson(courseId, 5, "Aula 5", "Descrição 5", "https://www.youtube.com/watch?v=l9CZykYZkOQ")
    ));

    List<Lesson> reindexedLessons = Lesson.reindexLessons(lessons);

    assertEquals(1, reindexedLessons.get(0).getIndexInCourse());
    assertEquals(2, reindexedLessons.get(1).getIndexInCourse());
    assertEquals(3, reindexedLessons.get(2).getIndexInCourse());
    assertEquals("Aula 1", reindexedLessons.get(0).getTitle());
    assertEquals("Aula 3", reindexedLessons.get(1).getTitle());
    assertEquals("Aula 5", reindexedLessons.get(2).getTitle());
  }

  @Test
  @DisplayName("happy path (sortLessonsByIndexInCourse): ordenar aulas por índice")
  void shouldSortLessonsByIndexInCourse() {
    String courseId = new ObjectId().toString();
    List<Lesson> lessons = new ArrayList<>(List.of(
        new Lesson(courseId, 2, "Aula 2", "Descrição 2", "https://www.youtube.com/watch?v=def456XYZ89"),
        new Lesson(courseId, 1, "Aula 1", "Descrição 1", "https://www.youtube.com/watch?v=abc123XYZ89"),
        new Lesson(courseId, 3, "Aula 3", "Descrição 3", "https://www.youtube.com/watch?v=ghi789XYZ89")
    ));

    Lesson.sortLessonsByIndexInCourse(lessons);

    assertEquals(1, lessons.get(0).getIndexInCourse());
    assertEquals(2, lessons.get(1).getIndexInCourse());
    assertEquals(3, lessons.get(2).getIndexInCourse());
    assertEquals("Aula 1", lessons.get(0).getTitle());
    assertEquals("Aula 2", lessons.get(1).getTitle());
    assertEquals("Aula 3", lessons.get(2).getTitle());
  }
}
