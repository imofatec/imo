package com.imo.backend.unit.catalog;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.imo.backend.contexts.catalog.course.Course;
import com.imo.backend.contexts.catalog.course.CoursePolicies;
import com.imo.backend.contexts.catalog.course.repositories.CourseRepository;
import com.imo.backend.contexts.catalog.course.value_objects.Categories;
import com.imo.backend.contexts.common.Slug;
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
class CoursePoliciesTest {

  @Mock private CourseRepository courseRepository;

  @InjectMocks private CoursePolicies coursePolicies;

  private List<Course> createContributorCourses() {
    Course existingCourse =
        new Course(
            true,
            "69cfe4f0c3fc43750401da4b",
            "Curso Java",
            "Iniciante",
            Categories.AI,
            "Descrição do curso",
            "https://www.youtube.com/watch?v=abc123XYZ89",
            1);

    Course existingCourse2 =
        new Course(
            true,
            new ObjectId().toString(),
            "Curso Python",
            "Avançado",
            Categories.DATA,
            "Descrição do curso de Python",
            "https://www.youtube.com/watch?v=l9CZykYZkOQ",
            1);

    return List.of(existingCourse, existingCourse2);
  }

  @Test
  @DisplayName("happy path (checkSlugConflict): permitir slug único para o contribuinte")
  void shouldAllowUniqueSlug() {
    String contributorId = "69cfe4f0c3fc43750401da4b";
    String newSlug = Slug.create("curso-go");

    when(this.courseRepository.findAllByContributorId(contributorId))
        .thenReturn(createContributorCourses());

    assertDoesNotThrow(() -> this.coursePolicies.checkSlugConflict(contributorId, newSlug, null));

    verify(this.courseRepository).findAllByContributorId(contributorId);
  }

  @Test
  @DisplayName("exception (checkSlugConflict): rejeitar slug já existente para o contribuinte")
  void shouldThrowWhenCourseSlugAlreadyExists() {
    String contributorId = "69cfe4f0c3fc43750401da4b";
    String currentCourseId = "curso-existente";
    String newSlug = Slug.create("curso-python");

    when(this.courseRepository.findAllByContributorId(contributorId))
        .thenReturn(createContributorCourses());

    assertThrows(
        ConflictException.class,
        () -> this.coursePolicies.checkSlugConflict(contributorId, newSlug, currentCourseId));

    verify(this.courseRepository).findAllByContributorId(contributorId);
  }

  @Test
  @DisplayName("happy path (checkSlugConflict): ignorar o próprio curso na atualização")
  void shouldIgnoreCurrentCourseWhenUpdating() {
    String contributorId = "69cfe4f0c3fc43750401da4b";
    List<Course> contributorCourses = createContributorCourses();
    Course existingCourse = contributorCourses.getFirst();
    String sameSlug = existingCourse.getName().slug();

    when(this.courseRepository.findAllByContributorId(contributorId))
        .thenReturn(contributorCourses);

    assertDoesNotThrow(
        () ->
            this.coursePolicies.checkSlugConflict(contributorId, sameSlug, existingCourse.getId()));

    verify(this.courseRepository).findAllByContributorId(contributorId);
  }
}
