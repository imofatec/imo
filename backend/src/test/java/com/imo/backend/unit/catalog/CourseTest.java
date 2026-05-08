package com.imo.backend.unit.catalog;

import static org.junit.jupiter.api.Assertions.*;

import com.imo.backend.contexts.catalog.course.Categories;
import com.imo.backend.contexts.catalog.course.Course;
import com.imo.backend.contexts.common.exceptions.custom.BadRequestException;
import com.imo.backend.contexts.common.exceptions.custom.ForbiddenException;
import java.util.List;
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
        1,
        List.of(new ObjectId().toString(), new ObjectId().toString()));
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

  @Test
  @DisplayName(
      "happy path (constructor): armazenar ids de skills como ObjectId e expor como string")
  void shouldStoreSkillIds() {
    List<String> skillIds = List.of(new ObjectId().toString(), new ObjectId().toString());

    Course course =
        new Course(
            true,
            new ObjectId().toString(),
            "Curso Java",
            "Iniciante",
            Categories.AI,
            "Curso sobre ia no java",
            "https://www.youtube.com/watch?v=abc123XYZ89&pp=test",
            1,
            skillIds);

    assertEquals(skillIds, course.getSkillIdsAsString());
  }

  @Test
  @DisplayName("exception (setSkillIds): rejeitar curso sem skills")
  void shouldRejectCourseWithoutSkills() {
    Course course = createCourse();

    assertThrows(BadRequestException.class, () -> course.setSkillIds(List.of()));
  }

  @Test
  @DisplayName("exception (setSkillIds): rejeitar curso com mais de 2 skills")
  void shouldRejectCourseWithMoreThanTwoSkills() {
    Course course = createCourse();

    assertThrows(
        BadRequestException.class,
        () ->
            course.setSkillIds(
                List.of(
                    new ObjectId().toString(),
                    new ObjectId().toString(),
                    new ObjectId().toString())));
  }

  @Test
  @DisplayName("exception (setSkillIds): rejeitar skills duplicadas")
  void shouldRejectDuplicatedSkills() {
    Course course = createCourse();
    String duplicatedSkillId = new ObjectId().toString();

    assertThrows(
        BadRequestException.class,
        () -> course.setSkillIds(List.of(duplicatedSkillId, duplicatedSkillId)));
  }

  @Test
  @DisplayName("exception (setSkillIds): rejeitar skillId inválido")
  void shouldRejectInvalidSkillId() {
    Course course = createCourse();

    assertThrows(BadRequestException.class, () -> course.setSkillIds(List.of("invalid-id")));
  }

  @Test
  @DisplayName("happy path (assertCanProgress): permitir progressão em curso ativo")
  void shouldAllowProgressWhenCourseIsActive() {
    Course course = createCourse();

    assertDoesNotThrow(course::assertCanProgress);
  }

  @Test
  @DisplayName("exception (assertCanProgress): bloquear progressão em curso inativo")
  void shouldThrowWhenCourseIsInactive() {
    Course course = createCourse();
    course.toggleStatus();

    assertThrows(ForbiddenException.class, course::assertCanProgress);
  }
}
