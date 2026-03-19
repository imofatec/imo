package com.imo.backend.contexts.catalog.lesson.services.impl;

import com.imo.backend.contexts.catalog.course.events.IncLessonsCountEvent;
import com.imo.backend.contexts.catalog.course.events.UpdateCourseLessonsCountEvent;
import com.imo.backend.contexts.catalog.lesson.Lesson;
import com.imo.backend.contexts.catalog.lesson.actions.commands.CreateLessonCommand; // ✅ Usando Command
import com.imo.backend.contexts.catalog.lesson.actions.factory.LessonFactory;
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
    public List<Lesson> execute(List<CreateLessonCommand> commands, String courseId) {
        this.checkConflictLessons(commands);

        var newLessons = this.lessonRepository.saveAll(LessonFactory.createLesson(commands, courseId));

        this.applicationEventPublisher.publishEvent(new UpdateCourseLessonsCountEvent(
            courseId,
            newLessons.size()
        ));

        this.applicationEventPublisher.publishEvent(new ReevaluateProgressEvent(courseId));

        return newLessons;
    }

    @Override
    public Lesson execute(CreateLessonCommand command, String courseId) {
        var existingLessons = lessonRepository.findAllByCourseId(courseId);

        existingLessons.forEach(existingLesson -> {
            if (existingLesson.getTitle().equals(command.title())) {
                throw new ConflictException("Já existe uma aula com esse título");
            }

            if (existingLesson.getYoutubeLink().equals(command.youtubeLink())) {
                throw new ConflictException("Já existe uma aula com este link de vídeo");
            }
        });

        Lesson newLesson = this.lessonRepository.save(LessonFactory.createLesson(
            command,
            existingLessons.size() + 1,
            courseId
        ));

        this.applicationEventPublisher.publishEvent(new IncLessonsCountEvent(courseId));
        this.applicationEventPublisher.publishEvent(new ReevaluateProgressEvent(courseId));

        return newLesson;
    }

    private void checkConflictLessons(List<CreateLessonCommand> commands) { // ✅ Trocado para Command
        var titles = new HashSet<String>();
        var descriptions = new HashSet<String>();
        var youtubeLinks = new HashSet<String>();

        commands.forEach(command -> {
            if (!titles.add(command.title())) {
                throw new ConflictException(String.format("Titulo '%s' repetido", command.title()));
            }

            if (command.description() != null && !descriptions.add(command.description())) {
                String shortDesc = command.description().length() > 10 
                    ? command.description().substring(0, 10) 
                    : command.description();
                throw new ConflictException(String.format("Descrição '%s...' repetida", shortDesc));
            }

            if (!youtubeLinks.add(command.youtubeLink())) {
                throw new ConflictException(String.format("Aula '%s' repetida", command.youtubeLink()));
            }
        });
    }
}
