package com.imo.backend.unit.journey_tracking;

import com.imo.backend.contexts.common.exceptions.custom.BadRequestException;
import com.imo.backend.contexts.common.exceptions.custom.ConflictException;
import com.imo.backend.contexts.journey_tracking.Progress;
import com.imo.backend.contexts.journey_tracking.value_objects.ProgressStatus;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProgressTest {

  @Test
  @DisplayName("happy path (construtor): criar progresso com 1 aula assistida de 4")
  void shouldCreateInProgressWhenNotAllLessonsWatched() {
    String userId = new ObjectId().toString();
    String courseId = new ObjectId().toString();
    String lessonId = new ObjectId().toString();

    Progress progress = new Progress(userId, courseId, List.of(lessonId), 4);

    assertEquals(ProgressStatus.IN_PROGRESS, progress.getStatus());
    assertNotNull(progress.getProgressPeriod().startedAt());
    assertNull(progress.getProgressPeriod().finishedAt());
    assertEquals(1, progress.getLessonsWatched().size());
    assertEquals(lessonId, progress.getLessonsWatched().getFirst());
    assertEquals(userId, progress.getUserId());
    assertEquals(courseId, progress.getCourseId());
  }

  @Test
  @DisplayName("happy path (construtor): assistir todas as aulas de uma vez")
  void shouldCreateFinishedWhenAllLessonsWatched() {
    String userId = new ObjectId().toString();
    String courseId = new ObjectId().toString();
    String lesson1 = new ObjectId().toString();
    String lesson2 = new ObjectId().toString();

    Progress progress = new Progress(userId, courseId, List.of(lesson1, lesson2), 2);

    assertEquals(ProgressStatus.FINISHED, progress.getStatus());
    assertNotNull(progress.getProgressPeriod().startedAt());
    assertNotNull(progress.getProgressPeriod().finishedAt());
    assertEquals(2, progress.getLessonsWatched().size());
  }

  @Test
  @DisplayName("happy path (construtor): curso com 1 aula")
  void shouldFinishImmediatelyWhenCourseHasOneLesson() {
    String userId = new ObjectId().toString();
    String courseId = new ObjectId().toString();
    String lessonId = new ObjectId().toString();

    Progress progress = new Progress(userId, courseId, List.of(lessonId), 1);

    assertEquals(ProgressStatus.FINISHED, progress.getStatus());
    assertNotNull(progress.getProgressPeriod().finishedAt());
  }

  @Test
  @DisplayName("exception (construtor): lista vazia de aulas")
  void shouldThrowBadRequestWhenEmptyLessons() {
    String userId = new ObjectId().toString();
    String courseId = new ObjectId().toString();

    assertThrows(BadRequestException.class, () -> new Progress(userId, courseId, List.of(), 4));
  }

  @Test
  @DisplayName("happy path (watchLesson): adicionar aula com sucesso")
  void shouldWatchLessonSuccessfully() {
    Progress progress = new Progress(
        new ObjectId().toString(),
        new ObjectId().toString(),
        List.of(new ObjectId().toString()),
        4
    );
    String newLesson = new ObjectId().toString();

    progress.watchLesson(newLesson, 4);

    assertTrue(progress.getLessonsWatched().contains(newLesson));
    assertEquals(2, progress.getLessonsWatched().size());
    assertEquals(ProgressStatus.IN_PROGRESS, progress.getStatus());
  }

  @Test
  @DisplayName("happy path (watchLesson): última aula")
  void shouldFinishWhenLastLessonWatched() {
    String lesson1 = new ObjectId().toString();
    String lesson2 = new ObjectId().toString();
    Progress progress = new Progress(
        new ObjectId().toString(),
        new ObjectId().toString(),
        List.of(lesson1),
        2
    );

    progress.watchLesson(lesson2, 2);

    assertEquals(ProgressStatus.FINISHED, progress.getStatus());
    assertNotNull(progress.getProgressPeriod().finishedAt());
    assertEquals(2, progress.getLessonsWatched().size());
  }

  @Test
  @DisplayName("exception (watchLesson): aula duplicada")
  void shouldThrowConflictOnDuplicateLesson() {
    String lessonId = new ObjectId().toString();
    Progress progress = new Progress(
        new ObjectId().toString(),
        new ObjectId().toString(),
        List.of(lessonId),
        4
    );

    assertThrows(ConflictException.class, () -> progress.watchLesson(lessonId, 4));
  }

  @Test
  @DisplayName("exception (watchLesson): curso já finalizado")
  void shouldThrowBadRequestWhenAlreadyFinished() {
    String lesson1 = new ObjectId().toString();
    String lesson2 = new ObjectId().toString();
    Progress progress = new Progress(
        new ObjectId().toString(),
        new ObjectId().toString(),
        List.of(lesson1, lesson2),
        2
    );

    assertEquals(ProgressStatus.FINISHED, progress.getStatus());

    assertThrows(
        BadRequestException.class,
        () -> progress.watchLesson(new ObjectId().toString(), 2)
    );
  }

  @Test
  @DisplayName("functional (watchLesson): startedAt preservado ao adicionar aulas")
  void shouldPreserveStartedAtWhenWatchingMoreLessons() throws InterruptedException {
    String lesson1 = new ObjectId().toString();
    String lesson2 = new ObjectId().toString();
    Progress progress = new Progress(
        new ObjectId().toString(),
        new ObjectId().toString(),
        List.of(lesson1),
        3
    );

    var originalStartedAt = progress.getProgressPeriod().startedAt();

    Thread.sleep(10);

    progress.watchLesson(lesson2, 3);

    assertEquals(originalStartedAt, progress.getProgressPeriod().startedAt());
    assertNull(progress.getProgressPeriod().finishedAt());
  }

  @Test
  @DisplayName("happy path (reevaluateStructure): remover aula que não existe mais no curso e continuar IN_PROGRESS")
  void shouldRemoveDeletedLesson() {
    String userId = new ObjectId().toString();
    String courseId = new ObjectId().toString();

    String lesson1 = new ObjectId().toString();
    String lesson2 = new ObjectId().toString();
    String lesson3 = new ObjectId().toString();

    Progress progress = new Progress(userId, courseId, List.of(lesson1, lesson2), 3);

    List<String> updatedLessonsListInCourse = List.of(lesson1, lesson3);
    boolean changed = progress.reevaluateStructure(updatedLessonsListInCourse);

    assertTrue(changed);
    assertEquals(ProgressStatus.IN_PROGRESS, progress.getStatus());
    assertNotEquals(updatedLessonsListInCourse, progress.getLessonsWatched());
    assertEquals(1, progress.getLessonsWatched().size());
  }

  @Test
  @DisplayName("functional (reevaluateStructure): trocar para FINISHED após remover aula que faltava assistir")
  void shouldRemoveDeletedLessonAndFinish() {
    String userId = new ObjectId().toString();
    String courseId = new ObjectId().toString();

    String lesson1 = new ObjectId().toString();
    String lesson2 = new ObjectId().toString();

    Progress progress = new Progress(userId, courseId, List.of(lesson1, lesson2), 3);

    List<String> updatedLessonsListInCourse = List.of(lesson1, lesson2);

    boolean changed = progress.reevaluateStructure(updatedLessonsListInCourse);

    assertTrue(changed);
    assertEquals(ProgressStatus.FINISHED, progress.getStatus());
    assertEquals(updatedLessonsListInCourse, progress.getLessonsWatched());
    assertEquals(2, progress.getLessonsWatched().size());
  }

  @Test
  @DisplayName("functional (reevaluateStructure): reverter FINISHED para IN_PROGRESS ao adicionar aulas")
  void shouldRevertToInProgressWhenNewLessons() {
    String userId = new ObjectId().toString();
    String courseId = new ObjectId().toString();

    String lesson1 = new ObjectId().toString();
    String lesson2 = new ObjectId().toString();

    Progress progress = new Progress(userId, courseId, List.of(lesson1, lesson2), 2);

    String lesson3 = new ObjectId().toString();

    List<String> updatedLessonsListInCourse = List.of(lesson1, lesson2, lesson3);

    boolean changed = progress.reevaluateStructure(updatedLessonsListInCourse);

    assertTrue(changed);
    assertEquals(ProgressStatus.IN_PROGRESS, progress.getStatus());
    assertNotEquals(updatedLessonsListInCourse, progress.getLessonsWatched());
    assertEquals(2, progress.getLessonsWatched().size());
  }

  @Test
  @DisplayName("edge (reevaluateStructure): teve mudança no curso mas isso nao afeta o status desse progresso específico")
  void shouldDoNothing() {
    String userId = new ObjectId().toString();
    String courseId = new ObjectId().toString();

    String lesson1 = new ObjectId().toString();
    String lesson2 = new ObjectId().toString();

    Progress progress = new Progress(userId, courseId, List.of(lesson1, lesson2), 5);

    String lesson3 = new ObjectId().toString();
    String lesson4 = new ObjectId().toString();

    List<String> updatedLessonsListInCourse = List.of(lesson1, lesson2, lesson3, lesson4);

    boolean changed = progress.reevaluateStructure(updatedLessonsListInCourse);

    assertFalse(changed);
    assertEquals(ProgressStatus.IN_PROGRESS, progress.getStatus());
    assertNotEquals(updatedLessonsListInCourse, progress.getLessonsWatched());
    assertEquals(2, progress.getLessonsWatched().size());
  }
}
