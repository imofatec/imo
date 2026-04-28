package com.imo.backend.unit.journey_tracking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.imo.backend.contexts.catalog.course.Course;
import com.imo.backend.contexts.catalog.course.repositories.CourseRepository;
import com.imo.backend.contexts.catalog.course.value_objects.Categories;
import com.imo.backend.contexts.journey_tracking.Progress;
import com.imo.backend.contexts.journey_tracking.events.CourseFinishedEvent;
import com.imo.backend.contexts.journey_tracking.repositories.ProgressRepository;
import com.imo.backend.contexts.journey_tracking.usecases.WatchLessonByIdUseCase;
import com.imo.backend.contexts.journey_tracking.value_objects.ProgressStatus;
import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

@ExtendWith(MockitoExtension.class)
class WatchLessonByIdUseCaseTest {

  @Mock private ProgressRepository progressRepository;
  @Mock private CourseRepository courseRepository;
  @Mock private ApplicationEventPublisher applicationEventPublisher;

  @InjectMocks private WatchLessonByIdUseCase useCase;

  private Course createCourse(String courseId, int lessonsCount) {
    Course course =
        new Course(
            true,
            new ObjectId().toString(),
            "Curso Java Completo",
            "Iniciante",
            Categories.DEV_WEB,
            "Descrição do curso java",
            "https://www.youtube.com/watch?v=abc123XYZ89",
            lessonsCount,
            List.of(new ObjectId().toString()));
    course.setId(courseId);
    return course;
  }

  @Test
  @DisplayName("happy path (execute): publicar evento quando curso de 1 aula é finalizado")
  void shouldPublishEventWhenSingleLessonCourseIsFinished() {
    String userId = new ObjectId().toString();
    String courseId = new ObjectId().toString();
    String lessonId = new ObjectId().toString();

    Course course = createCourse(courseId, 1);

    when(this.courseRepository.findByLessonIdOrThrow(lessonId)).thenReturn(course);
    when(this.progressRepository.findByUserIdAndCourseId(userId, courseId))
        .thenReturn(Optional.empty());
    when(this.progressRepository.save(org.mockito.ArgumentMatchers.any(Progress.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));

    Progress savedProgress = this.useCase.execute(lessonId, userId);

    assertEquals(ProgressStatus.FINISHED, savedProgress.getStatus());
    verify(this.applicationEventPublisher)
        .publishEvent(
            argThat(
                (Object event) ->
                    event instanceof CourseFinishedEvent courseFinishedEvent
                        && courseFinishedEvent.userId().equals(userId)
                        && courseFinishedEvent.courseId().equals(courseId)));
  }

  @Test
  @DisplayName("functional (execute): não publicar evento quando ainda restam aulas")
  void shouldNotPublishEventWhenCourseIsStillInProgress() {
    String userId = new ObjectId().toString();
    String courseId = new ObjectId().toString();
    String firstLessonId = new ObjectId().toString();
    String secondLessonId = new ObjectId().toString();

    Course course = createCourse(courseId, 3);
    Progress progress = new Progress(userId, courseId, List.of(firstLessonId), 3);

    when(this.courseRepository.findByLessonIdOrThrow(secondLessonId)).thenReturn(course);
    when(this.progressRepository.findByUserIdAndCourseId(userId, courseId))
        .thenReturn(Optional.of(progress));
    when(this.progressRepository.save(progress)).thenReturn(progress);

    Progress savedProgress = this.useCase.execute(secondLessonId, userId);

    assertEquals(ProgressStatus.IN_PROGRESS, savedProgress.getStatus());
    verify(this.applicationEventPublisher, never())
        .publishEvent(org.mockito.ArgumentMatchers.any());
  }

  @Test
  @DisplayName("functional (execute): não publicar evento quando o curso já estava finalizado")
  void shouldNotPublishEventWhenCourseIsAlreadyFinished() {
    String userId = new ObjectId().toString();
    String courseId = new ObjectId().toString();
    String lessonId = new ObjectId().toString();

    Course course = createCourse(courseId, 1);
    Progress progress = new Progress(userId, courseId, List.of(lessonId), 1);

    when(this.courseRepository.findByLessonIdOrThrow(lessonId)).thenReturn(course);
    when(this.progressRepository.findByUserIdAndCourseId(userId, courseId))
        .thenReturn(Optional.of(progress));

    Progress savedProgress = this.useCase.execute(lessonId, userId);

    assertEquals(ProgressStatus.FINISHED, savedProgress.getStatus());
    verify(this.progressRepository, never()).save(progress);
    verify(this.applicationEventPublisher, never())
        .publishEvent(org.mockito.ArgumentMatchers.any());
  }
}
