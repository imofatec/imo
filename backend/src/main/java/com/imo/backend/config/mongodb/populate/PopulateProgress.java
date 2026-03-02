package com.imo.backend.config.mongodb.populate;

import com.imo.backend.modules.course.Course;
import com.imo.backend.modules.course.repositories.CourseRepository;
import com.imo.backend.modules.lesson.Lesson;
import com.imo.backend.modules.lesson.repositories.LessonRepository;
import com.imo.backend.modules.progress.orchestrators.WatchLessonByIdOrchestratorImpl;
import com.imo.backend.modules.user.User;
import com.imo.backend.modules.user.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class PopulateProgress {
  private final UserRepository userRepository;
  private final CourseRepository courseRepository;
  private final LessonRepository lessonRepository;
  private final WatchLessonByIdOrchestratorImpl watchLessonByIdOrchestrator;

  public PopulateProgress(
      UserRepository userRepository,
      CourseRepository courseRepository,
      LessonRepository lessonRepository,
      WatchLessonByIdOrchestratorImpl watchLessonByIdOrchestrator
  ) {
    this.userRepository = userRepository;
    this.courseRepository = courseRepository;
    this.lessonRepository = lessonRepository;
    this.watchLessonByIdOrchestrator = watchLessonByIdOrchestrator;
  }

  public void execute() {
    List<User> users = userRepository.findAll();
    List<Course> courses = courseRepository.findAll();

    double inactive = 3.5;
    double casual = 4.0;
    double engaged = 2.0;
    double tryhard = 0.5;

    double totalWeight = inactive + casual + engaged + tryhard;

    double limitInactive = inactive / totalWeight;
    double limitCasual = limitInactive + casual / totalWeight;
    double limitEngaged = limitCasual + engaged / totalWeight;

    for (User user : users) {
      double roll = ThreadLocalRandom.current().nextDouble();

      int coursesToTake;

      if (roll < limitInactive) {
        coursesToTake = 0;
      } else if (roll < limitCasual) {
        coursesToTake = ThreadLocalRandom.current().nextInt(1, 4);
      } else if (roll < limitEngaged) {
        coursesToTake = ThreadLocalRandom.current().nextInt(2, 4);
      } else {
        coursesToTake = ThreadLocalRandom.current().nextInt(4, 7);
      }

      Set<String> chosenCourseIds = new HashSet<>();

      for (int i = 0; i < coursesToTake; i++) {
        Course course;
        do {
          course = courses.get(ThreadLocalRandom.current().nextInt(courses.size()));
        } while (chosenCourseIds.contains(course.getId()));

        chosenCourseIds.add(course.getId());

        int lessonsWatched = ThreadLocalRandom.current().nextInt(1, course.getLessonsCount() + 1);

        List<Lesson> lessons = this.lessonRepository.findAllByCourseId(course.getId());

        for (int j = 0; j < lessonsWatched; j++) {
          String lessonId = lessons.get(j).getId();
          watchLessonByIdOrchestrator.execute(lessonId, user.getId());
        }
      }
    }
  }
}
