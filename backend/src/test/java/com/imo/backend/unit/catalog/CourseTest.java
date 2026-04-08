package com.imo.backend.unit.catalog;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.imo.backend.contexts.catalog.course.Course;
import com.imo.backend.contexts.catalog.course.value_objects.Categories;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CourseTest {

  private Course createCourse() {
    return new Course(
        true,
        new ObjectId().toString(),
        "Curso Java",
        "Iniciante",
        Categories.AI,
        "Curso sobre ia no java",
        "https://www.youtube.com/watch?v=abc123XYZ89&pp=test",
        1);
  }

  @Test
  @DisplayName("happy path (construtor): normalizar firstLessonYoutubeLink e gerar slug do nome")
  void shouldCreateCourseWithNormalizedFirstLessonAndNameSlug() {
    Course course = createCourse();

    assertEquals("abc123XYZ89", course.getFirstLessonYoutubeLink());
    assertEquals("curso-java", course.getName().slug());
  }

  @Test
  @DisplayName("happy path (construtor): armazenar categoria e nível corretamente")
  void shouldStoreCategoryAndLevel() {
    Course course = createCourse();

    assertEquals("Inteligência artificial", course.getCategory().name());
    assertEquals("inteligencia-artificial", course.getCategory().slug());
    assertEquals("Iniciante", course.getLevel().name());
    assertEquals("iniciante", course.getLevel().slug());
  }

  @Test
  @DisplayName("happy path (toggleStatus): alternar status do curso")
  void shouldToggleCourseStatus() {
    Course course = createCourse();

    assertTrue(course.isActive());

    course.toggleStatus();
    assertFalse(course.isActive());

    course.toggleStatus();
    assertTrue(course.isActive());
  }

  @Test
  @DisplayName("happy path (setFirstLessonYoutubeLink): normalizar link ao atualizar")
  void shouldNormalizeFirstLessonYoutubeLinkWhenUpdating() {
    Course course = createCourse();

    course.setFirstLessonYoutubeLink("https://www.youtube.com/watch?v=SR1OfCAmTyI");

    assertEquals("SR1OfCAmTyI", course.getFirstLessonYoutubeLink());
  }
}
