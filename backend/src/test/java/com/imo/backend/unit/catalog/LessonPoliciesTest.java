package com.imo.backend.unit.catalog;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.imo.backend.contexts.catalog.lesson.Lesson;
import com.imo.backend.contexts.catalog.lesson.LessonPolicies;
import com.imo.backend.contexts.catalog.lesson.commands.CreateLessonCommand;
import com.imo.backend.contexts.catalog.lesson.repositories.LessonRepository;
import com.imo.backend.contexts.common.exceptions.custom.ConflictException;
import java.util.List;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LessonPoliciesTest {

  @Mock private LessonRepository lessonRepository;

  @InjectMocks private LessonPolicies lessonPolicies;

  private List<Lesson> createExistingLessons(String courseId) {
    Lesson existingLesson1 =
        new Lesson(
            courseId, 1, "Aula2", "Aula 2 de java", "https://www.youtube.com/watch?v=def456XYZ89");
    existingLesson1.setId(new ObjectId().toString());

    Lesson existingLesson2 =
        new Lesson(
            courseId, 2, "Aula3", "Aula 3 de java", "https://www.youtube.com/watch?v=ghi789XYZ89");
    existingLesson2.setId(new ObjectId().toString());

    return List.of(existingLesson1, existingLesson2);
  }

  private List<CreateLessonCommand> createLessonCommands() {
    CreateLessonCommand lesson1 =
        new CreateLessonCommand(
            "DevOps", "Primeiros passos com Java", "https://www.youtube.com/watch?v=abc123XYZ89");

    CreateLessonCommand lesson2 =
        new CreateLessonCommand(
            "Introducao ao Java",
            "Conceitos de classes e objetos",
            "https://www.youtube.com/watch?v=def456XYZ89");

    CreateLessonCommand lesson3 =
        new CreateLessonCommand(
            "Colecoes em Java",
            "Uso de listas e mapas",
            "https://www.youtube.com/watch?v=ghi789XYZ89");

    return List.of(lesson1, lesson2, lesson3);
  }

  @Test
  @DisplayName(
      "happy path (checkLessonConflicts): aceitar título, descrição e link únicos no curso")
  void shouldAllowUniqueLessonFieldsInCourse() {
    String courseId = new ObjectId().toString();
    String currentLessonId = new ObjectId().toString();
    String newTitle = "Aula1";
    String newLink = "https://www.youtube.com/watch?v=abc123XYZ89&pp=test";
    String newDescription = "Aula 1 de java";

    when(this.lessonRepository.findAllByCourseId(courseId))
        .thenReturn(createExistingLessons(courseId));

    assertDoesNotThrow(
        () ->
            this.lessonPolicies.checkLessonConflicts(
                courseId, currentLessonId, newTitle, newLink, newDescription));

    verify(this.lessonRepository).findAllByCourseId(courseId);
  }

  @Test
  @DisplayName("happy path (checkListInternalConflicts): aceitar lista com aulas únicas")
  void shouldAllowUniqueLessonList() {
    assertDoesNotThrow(
        () -> this.lessonPolicies.checkListInternalConflicts(createLessonCommands()));
  }

  @Test
  @DisplayName("exception (checkLessonConflicts): rejeitar título repetido no curso")
  void shouldThrowWhenTitleIsDuplicated() {
    String courseId = new ObjectId().toString();
    String currentLessonId = new ObjectId().toString();
    String newTitle = "Aula2";
    String newLink = "https://www.youtube.com/watch?v=abc123XYZ89&pp=test";
    String newDescription = "Aula 1 de java";

    when(this.lessonRepository.findAllByCourseId(courseId))
        .thenReturn(createExistingLessons(courseId));

    assertThrows(
        ConflictException.class,
        () ->
            this.lessonPolicies.checkLessonConflicts(
                courseId, currentLessonId, newTitle, newLink, newDescription));

    verify(this.lessonRepository).findAllByCourseId(courseId);
  }

  @Test
  @DisplayName("exception (checkLessonConflicts): rejeitar descrição repetida no curso")
  void shouldThrowWhenDescriptionIsDuplicated() {
    String courseId = new ObjectId().toString();
    String currentLessonId = new ObjectId().toString();
    String newTitle = "Aula 4";
    String newLink = "https://www.youtube.com/watch?v=abc123XYZ89&pp=test";
    String newDescription = "Aula 2 de java";

    when(this.lessonRepository.findAllByCourseId(courseId))
        .thenReturn(createExistingLessons(courseId));

    assertThrows(
        ConflictException.class,
        () ->
            this.lessonPolicies.checkLessonConflicts(
                courseId, currentLessonId, newTitle, newLink, newDescription));

    verify(this.lessonRepository).findAllByCourseId(courseId);
  }

  @Test
  @DisplayName("exception (checkLessonConflicts): rejeitar link repetido no curso")
  void shouldThrowWhenLinkIsDuplicated() {
    String courseId = new ObjectId().toString();
    String currentLessonId = new ObjectId().toString();
    String newTitle = "Aula 4";
    String newLink = "https://www.youtube.com/watch?v=def456XYZ89";
    String newDescription = "Aula 4 de java";

    when(this.lessonRepository.findAllByCourseId(courseId))
        .thenReturn(createExistingLessons(courseId));

    assertThrows(
        ConflictException.class,
        () ->
            this.lessonPolicies.checkLessonConflicts(
                courseId, currentLessonId, newTitle, newLink, newDescription));

    verify(this.lessonRepository).findAllByCourseId(courseId);
  }

  @Test
  @DisplayName("exception (checkListInternalConflicts): rejeitar lista com títulos duplicados")
  void shouldThrowWhenTitlesConflictInsideList() {
    CreateLessonCommand lessonWithConflict =
        new CreateLessonCommand(
            "Introducao ao Java",
            "Primeiros passos com Java",
            "https://www.youtube.com/watch?v=abc123XYZ89");

    List<CreateLessonCommand> commands = createLessonCommands();
    List<CreateLessonCommand> lessonsWithConflict =
        List.of(commands.get(0), commands.get(1), commands.get(2), lessonWithConflict);

    assertThrows(
        ConflictException.class,
        () -> this.lessonPolicies.checkListInternalConflicts(lessonsWithConflict));
  }

  @Test
  @DisplayName("exception (checkListInternalConflicts): rejeitar lista com links duplicados")
  void shouldThrowWhenLinksConflictInsideList() {
    CreateLessonCommand lessonWithConflict =
        new CreateLessonCommand(
            "Java Avançado", "OOP no java", "https://www.youtube.com/watch?v=ghi789XYZ89");

    List<CreateLessonCommand> commands = createLessonCommands();
    List<CreateLessonCommand> lessonsWithConflict =
        List.of(commands.get(0), commands.get(1), commands.get(2), lessonWithConflict);

    assertThrows(
        ConflictException.class,
        () -> this.lessonPolicies.checkListInternalConflicts(lessonsWithConflict));
  }

  @Test
  @DisplayName("exception (checkListInternalConflicts): rejeitar lista com descrições duplicadas")
  void shouldThrowWhenDescriptionsConflictInsideList() {
    CreateLessonCommand lessonWithConflict =
        new CreateLessonCommand(
            "Java Funcional",
            "Uso de listas e mapas",
            "https://www.youtube.com/watch?v=sck-5KPee9c");

    List<CreateLessonCommand> commands = createLessonCommands();
    List<CreateLessonCommand> lessonsWithConflict =
        List.of(commands.get(0), commands.get(1), commands.get(2), lessonWithConflict);

    assertThrows(
        ConflictException.class,
        () -> this.lessonPolicies.checkListInternalConflicts(lessonsWithConflict));
  }
}
