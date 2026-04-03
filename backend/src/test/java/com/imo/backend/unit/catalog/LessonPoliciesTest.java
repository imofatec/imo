package com.imo.backend.unit.catalog;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.imo.backend.contexts.catalog.lesson.Lesson;
import com.imo.backend.contexts.catalog.lesson.LessonPolicies;
import com.imo.backend.contexts.catalog.lesson.commands.CreateLessonCommand;
import com.imo.backend.contexts.catalog.lesson.repositories.LessonRepository;
import com.imo.backend.contexts.common.exceptions.custom.ConflictException;

@ExtendWith(MockitoExtension.class)
public class LessonPoliciesTest {

  @Mock
  private LessonRepository lessonRepository;

  @InjectMocks
  private LessonPolicies lessonPolicies;

  private List<Lesson> createLessonMock(String courseId) {
    Lesson existingLesson1 = new Lesson(
        courseId,
        1,
        "Aula2",
        "Aula 2 de java",
        "https://www.youtube.com/watch?v=def456XYZ89");

    Lesson existingLesson2 = new Lesson(
        courseId,
        2,
        "Aula3",
        "Aula 3 de java",
        "https://www.youtube.com/watch?v=ghi789XYZ89");

    return List.of(existingLesson1, existingLesson2);
  }

  private List<CreateLessonCommand> createLesson() {
    CreateLessonCommand lesson1 = new CreateLessonCommand(
        "DevOps",
        "Primeiros passos com Java",
        "https://www.youtube.com/watch?v=abc123XYZ89");

    CreateLessonCommand lesson2 = new CreateLessonCommand(
        "Introducao ao Java",
        "Conceitos de classes e objetos",
        "https://www.youtube.com/watch?v=def456XYZ89");

    CreateLessonCommand lesson3 = new CreateLessonCommand(
        "Colecoes em Java",
        "Uso de listas e mapas",
        "https://www.youtube.com/watch?v=ghi789XYZ89");

    return List.of(lesson1, lesson2, lesson3);
  }

  @Test
  @DisplayName("functional: aceitar 3 aulas unicas no mesmo curso")
  public void shouldValidateUniqueLessons() {
    String courseId = new ObjectId().toString();
    String currentLessonId = new ObjectId().toString();
    String newTitle = "Aula1";
    String newLink = "https://www.youtube.com/watch?v=abc123XYZ89&pp=test";
    String newDescription = "Aula 1 de java";

    when(lessonRepository.findAllByCourseId(courseId))
        .thenReturn(createLessonMock(courseId));

    assertDoesNotThrow(() -> this.lessonPolicies.checkLessonConflicts(courseId, currentLessonId, newTitle,
        newLink,
        newDescription));

    verify(lessonRepository).findAllByCourseId(courseId);

  }

  @Test
  @DisplayName("functional: aceitar lista com aulas unicas")
  public void shouldValidateUniqueLessonOnList() {

    assertDoesNotThrow(() -> this.lessonPolicies.checkListInternalConflicts(createLesson()));
  }

  @Test
  @DisplayName("exception: rejeitar título repetido")
  public void shouldThrowWhenTitleIsDuplicated() {
    String courseId = new ObjectId().toString();
    String currentLessonId = new ObjectId().toString();
    String newTitle = "Aula2";
    String newLink = "https://www.youtube.com/watch?v=abc123XYZ89&pp=test";
    String newDescription = "Aula 1 de java";

    when(lessonRepository.findAllByCourseId(courseId))
        .thenReturn(createLessonMock(courseId));

    assertThrows(ConflictException.class,
        () -> this.lessonPolicies.checkLessonConflicts(courseId, currentLessonId, newTitle,
            newLink,
            newDescription));

    verify(lessonRepository).findAllByCourseId(courseId);

  }

  @Test
  @DisplayName("exception: rejeitar descrição repetido")
  public void shouldThrowWhenDescriptionIsDuplicated() {
    String courseId = new ObjectId().toString();
    String currentLessonId = new ObjectId().toString();
    String newTitle = "Aula2";
    String newLink = "https://www.youtube.com/watch?v=abc123XYZ89&pp=test";
    String newDescription = "Aula 2 de java";

    when(lessonRepository.findAllByCourseId(courseId))
        .thenReturn(createLessonMock(courseId));

    assertThrows(ConflictException.class,
        () -> this.lessonPolicies.checkLessonConflicts(courseId, currentLessonId, newTitle,
            newLink,
            newDescription));

    verify(lessonRepository).findAllByCourseId(courseId);

  }

  @Test
  @DisplayName("exception: rejeitar link repetido")
  public void shouldThrowWhenLinkIsDuplicated() {
    String courseId = new ObjectId().toString();
    String currentLessonId = new ObjectId().toString();
    String newTitle = "Aula 4";
    String newLink = "https://www.youtube.com/watch?v=def456XYZ89";
    String newDescription = "Aula 4 de java";

    when(lessonRepository.findAllByCourseId(courseId))
        .thenReturn(createLessonMock(courseId));

    assertThrows(ConflictException.class,
        () -> this.lessonPolicies.checkLessonConflicts(courseId, currentLessonId, newTitle,
            newLink,
            newDescription));

    verify(lessonRepository).findAllByCourseId(courseId);

  }

  @Test
  @DisplayName("exception: não aceitar lista com aulas duplicadas")
  public void shouldThrowWhenTitlesLessonsConflicts() {

    CreateLessonCommand lesson1 = new CreateLessonCommand(
        "Introducao ao Java",
        "Primeiros passos com Java",
        "https://www.youtube.com/watch?v=abc123XYZ89");

    List<CreateLessonCommand> commands = createLesson();
    List<CreateLessonCommand> lessonsWithConflict = List.of(
        commands.get(0),
        commands.get(1),
        commands.get(2),
        lesson1);

    assertThrows(ConflictException.class,
        () -> this.lessonPolicies.checkListInternalConflicts(lessonsWithConflict));
  }

  @Test
  @DisplayName("exception: não aceitar lista com links duplicados")
  public void shouldThrowWhenLinksLessonsConflicts() {

    CreateLessonCommand lesson1 = new CreateLessonCommand(
        "Java Avançado",
        "OOP no java",
        "https://www.youtube.com/watch?v=ghi789XYZ89");

    List<CreateLessonCommand> commands = createLesson();
    List<CreateLessonCommand> lessonsWithConflict = List.of(
        commands.get(0),
        commands.get(1),
        commands.get(2),
        lesson1);

    assertThrows(ConflictException.class,
        () -> this.lessonPolicies.checkListInternalConflicts(lessonsWithConflict));
  }
}
