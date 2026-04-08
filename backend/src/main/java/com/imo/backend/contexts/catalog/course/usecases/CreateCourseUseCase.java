package com.imo.backend.contexts.catalog.course.usecases;

import com.imo.backend.contexts.catalog.course.Course;
import com.imo.backend.contexts.catalog.course.CoursePolicies;
import com.imo.backend.contexts.catalog.course.commands.CreateCourseCommand;
import com.imo.backend.contexts.catalog.course.http.dtos.CourseDTO;
import com.imo.backend.contexts.catalog.course.http.dtos.CourseDetailsDTO;
import com.imo.backend.contexts.catalog.course.repositories.CourseRepository;
import com.imo.backend.contexts.catalog.lesson.usecases.CreateLessonUseCase;
import com.imo.backend.contexts.common.Slug;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CreateCourseUseCase {
  private final CreateLessonUseCase createLessonUseCase;

  private final CourseRepository courseRepository;

  private final CoursePolicies coursePolicies;

  public CreateCourseUseCase(
      CreateLessonUseCase createLessonUseCase,
      CourseRepository courseRepository,
      CoursePolicies coursePolicies) {
    this.createLessonUseCase = createLessonUseCase;
    this.courseRepository = courseRepository;
    this.coursePolicies = coursePolicies;
  }

  public CourseDetailsDTO execute(CreateCourseCommand cmd, String contributorId) {
    var potentialNewSlugCourse = Slug.create(cmd.name());
    this.coursePolicies.checkSlugConflict(contributorId, potentialNewSlugCourse, null);

    var newCourse = this.courseRepository.save(new Course(true, cmd.contributorId(), cmd.name(), cmd.level(),
        cmd.category(), cmd.description(), cmd.lessons().getFirst().youtubeLink(), cmd.lessons().size()));

    var newLessons = this.createLessonUseCase.execute(cmd.lessons(), newCourse.getId());

    return new CourseDetailsDTO(CourseDTO.fromEntity(newCourse), newLessons);
  }
}
