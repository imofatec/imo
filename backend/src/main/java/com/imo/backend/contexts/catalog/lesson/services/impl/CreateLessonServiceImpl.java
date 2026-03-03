package com.imo.backend.contexts.catalog.lesson.services.impl;

import com.imo.backend.contexts.catalog.course.events.IncLessonsCountEvent;
import com.imo.backend.contexts.catalog.course.events.UpdateCourseLessonsCountEvent;
import com.imo.backend.contexts.catalog.lesson.Lesson;
import com.imo.backend.contexts.catalog.lesson.actions.factory.LessonFactory;
import com.imo.backend.contexts.catalog.lesson.actions.inputs.CreateLessonInput;
import com.imo.backend.contexts.catalog.lesson.repositories.LessonRepository;
import com.imo.backend.contexts.catalog.lesson.services.CreateLessonService;
import com.imo.backend.contexts.common.exceptions.custom.ConflictException;
import com.imo.backend.contexts.journey_tracking.events.ReevaluateProgressEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class CreateLessonServiceImpl implements CreateLessonService {
  private final LessonRepository lessonRepository;

  private final ApplicationEventPublisher applicationEventPublisher;

  public CreateLessonServiceImpl(
      LessonRepository lessonRepository,
      ApplicationEventPublisher applicationEventPublisher
  ) {
    this.lessonRepository = lessonRepository;
    this.applicationEventPublisher = applicationEventPublisher;
  }

  @Override
  public List<Lesson> execute(List<CreateLessonInput> lessonDto, String courseId) {
    this.checkConflictLessons(lessonDto);

    var newLessons = this.lessonRepository.saveAll(LessonFactory.createLesson(lessonDto, courseId));

    this.applicationEventPublisher.publishEvent(new UpdateCourseLessonsCountEvent(
        courseId,
        newLessons.size()
    ));

    this.applicationEventPublisher.publishEvent(new ReevaluateProgressEvent(courseId));

    return newLessons;
  }

  @Override
  public Lesson execute(CreateLessonInput lessonDto, String courseId) {
    var existingLessons = lessonRepository.findAllByCourseId(courseId);

    existingLessons.forEach(existingLesson -> {
      if (existingLesson.getTitle().equals(lessonDto.title())) {
        throw new ConflictException("Já existe uma aula com esse título");
      }

      if (existingLesson.getYoutubeLink().equals(lessonDto.youtubeLink())) {
        throw new ConflictException("Já existe uma aula com este link de vídeo");
      }
    });

    Lesson newLesson = this.lessonRepository.save(LessonFactory.createLesson(
        lessonDto,
        existingLessons.size() + 1,
        courseId
    ));

    this.applicationEventPublisher.publishEvent(new IncLessonsCountEvent(courseId));

    this.applicationEventPublisher.publishEvent(new ReevaluateProgressEvent(courseId));

    return newLesson;
  }

  private void checkConflictLessons(List<CreateLessonInput> lessons) {
    var titles = new HashSet<>();
    var descriptions = new HashSet<>();
    var youtubeLinks = new HashSet<>();

    lessons.forEach(lesson -> {
      if (!titles.add(lesson.title())) {
        throw new ConflictException(String.format("Titulo '%s' repetido", lesson.title()));
      }

      if (!descriptions.add(lesson.description())) {
        throw new ConflictException(String.format(
            "Descrição '%s...' repetida",
            lesson.description().substring(10)
        ));
      }

      if (!youtubeLinks.add(lesson.youtubeLink())) {
        throw new ConflictException(String.format("Aula '%s' repetida", lesson.youtubeLink()));
      }
    });
  }
}
