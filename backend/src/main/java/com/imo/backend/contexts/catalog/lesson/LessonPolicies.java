package com.imo.backend.contexts.catalog.lesson;

import com.imo.backend.contexts.catalog.lesson.commands.CreateLessonCommand;
import com.imo.backend.contexts.catalog.lesson.repositories.LessonRepository;
import com.imo.backend.contexts.common.exceptions.custom.ConflictException;
import java.util.HashSet;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class LessonPolicies {

  private final LessonRepository lessonRepository;

  public LessonPolicies(LessonRepository lessonRepository) {
    this.lessonRepository = lessonRepository;
  }

  public void checkLessonConflicts(
      String courseId,
      String currentLessonId,
      String newTitle,
      String newLink,
      String newDescription) {
    List<Lesson> existingLessons = this.lessonRepository.findAllByCourseId(courseId);

    existingLessons.forEach(
        lesson -> {
          if (currentLessonId != null && lesson.getId().equals(currentLessonId)) {
            return;
          }

          if (newTitle != null && lesson.getTitle().equals(newTitle)) {
            throw new ConflictException("Já existe uma aula com esse título");
          }

          if (newLink != null) {
            String formattedNewLink = Lesson.formatYoutubeLink(newLink);
            if (lesson.getYoutubeLink().equals(formattedNewLink)) {
              throw new ConflictException("Já existe uma aula com este link");
            }
          }

          if (newDescription != null && lesson.getDescription().equals(newDescription)) {
            throw new ConflictException("Já existe uma aula com essa descrição");
          }
        });
  }

  public void checkListInternalConflicts(List<CreateLessonCommand> commands) {
    var titles = new HashSet<String>();
    var descriptions = new HashSet<String>();
    var youtubeLinks = new HashSet<String>();

    commands.forEach(
        command -> {
          if (!titles.add(command.title())) {
            throw new ConflictException(
                String.format("Título '%s' repetido na lista", command.title()));
          }

          if (command.description() != null && !descriptions.add(command.description())) {
            throw new ConflictException(
                String.format("Descrição '%s' repetida na lista", command.description()));
          }

          if (!youtubeLinks.add(Lesson.formatYoutubeLink(command.youtubeLink()))) {
            throw new ConflictException(
                String.format("Link '%s' repetido na lista", command.youtubeLink()));
          }
        });
  }
}
