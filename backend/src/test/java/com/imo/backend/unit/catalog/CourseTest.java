package com.imo.backend.unit.catalog;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
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

import com.imo.backend.contexts.common.exceptions.custom.ConflictException;
import com.imo.backend.contexts.catalog.course.Course;
import com.imo.backend.contexts.catalog.course.repositories.CourseRepository;
import com.imo.backend.contexts.catalog.course.value_objects.Categories;
import com.imo.backend.contexts.common.Slug;
import com.imo.backend.contexts.catalog.course.CoursePolicies;
import com.imo.backend.contexts.catalog.course.commands.UpdateCourseByIdCommand;

@ExtendWith(MockitoExtension.class)
public class CourseTest {

  @Mock
  private CourseRepository courseRepository;

  @InjectMocks
  private CoursePolicies coursePolicies;

  private List<Course> createListCourse(String contributorId) {
    Course existingCourse = new Course(
        true,
        "69cfe4f0c3fc43750401da4b",
        "Curso Java",
        "Iniciante",
        Categories.AI,
        "Descrição do curso",
        "https://www.youtube.com/watch?v=abc123XYZ89",
        1);

    Course existingCourse2 = new Course(
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
  @DisplayName("happy path: criar curso com 1 aula válida")
  public void shouldCreateCourseWithOneLesson() {
    boolean isActive = true;
    String contributorId = new ObjectId().toString();
    String name = "Curso Java";
    String level = "Iniciante";
    Categories categories = Categories.AI;
    String description = "Curso sobre ia no java";
    String firstLesson = "https://www.youtube.com/watch?v=abc123XYZ89&pp=test";
    int lessonCount = 1;

    Course course = new Course(isActive, contributorId, name, level, categories, description, firstLesson,
        lessonCount);

    assertEquals("abc123XYZ89", course.getFirstLessonYoutubeLink());
    assertEquals("curso-java", course.getName().slug());
  }

  @Test
  @DisplayName("happy path: atualiza informações do curso")
  public void shouldUpdateCourseData() {
    Course course = new Course(
        true,
        new ObjectId().toString(),
        "Curso Java",
        "Iniciante",
        Categories.AI,
        "desc",
        "https://www.youtube.com/watch?v=sck-5KPee9c",
        1);

    UpdateCourseByIdCommand cmd = new UpdateCourseByIdCommand(
        "course-id",
        "Course Python",
        Categories.DATA,
        "Iniciante",
        null,
        null,
        "https://www.youtube.com/watch?v=SR1OfCAmTyI");

    Course.applyUpdate(course, cmd);

    assertEquals("course-python", course.getName().slug());
    assertEquals("dados", course.getCategory().slug());
    assertEquals("SR1OfCAmTyI", course.getFirstLessonYoutubeLink());
  }

  @Test
  @DisplayName("exception: retorna erro ao tentar atualizar curso com slug ja existente")
  public void shouldThrowWhenCourseSlugAlreadyExist() {

    String contributorId = "69cfe4f0c3fc43750401da4b";
    String courseId = "curso-existente";
    String newSlug = Slug.create("curso-python");

    when(courseRepository.findAllByContributorId(contributorId))
        .thenReturn(createListCourse(contributorId));

    assertThrows(ConflictException.class,
        () -> this.coursePolicies.checkUpdateConflict(courseId, contributorId, newSlug));

    verify(courseRepository).findAllByContributorId(contributorId);
  }
}
