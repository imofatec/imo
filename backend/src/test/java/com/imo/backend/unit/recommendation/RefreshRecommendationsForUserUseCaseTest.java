package com.imo.backend.unit.recommendation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.imo.backend.contexts.catalog.course.Course;
import com.imo.backend.contexts.catalog.course.repositories.CourseRepository;
import com.imo.backend.contexts.catalog.course.repositories.CourseSearchParams;
import com.imo.backend.contexts.catalog.course.value_objects.Categories;
import com.imo.backend.contexts.catalog.skill.Skill;
import com.imo.backend.contexts.catalog.skill.repositories.SkillRepository;
import com.imo.backend.contexts.common.CombineWith;
import com.imo.backend.contexts.common.MatchType;
import com.imo.backend.contexts.identity.user.User;
import com.imo.backend.contexts.identity.user.repositories.UserRepository;
import com.imo.backend.contexts.journey_tracking.Progress;
import com.imo.backend.contexts.journey_tracking.ProgressDetails;
import com.imo.backend.contexts.journey_tracking.repositories.ProgressRepository;
import com.imo.backend.contexts.recommendation.Recommendation;
import com.imo.backend.contexts.recommendation.repositories.RecommendationRepository;
import com.imo.backend.contexts.recommendation.usecases.RefreshRecommendationsForUserUseCase;
import com.imo.backend.contexts.skill_profile.SkillProfile;
import com.imo.backend.contexts.skill_profile.repositories.SkillProfileRepository;
import java.util.List;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RefreshRecommendationsForUserUseCaseTest {
  @Mock private UserRepository userRepository;
  @Mock private SkillProfileRepository skillProfileRepository;
  @Mock private ProgressRepository progressRepository;
  @Mock private CourseRepository courseRepository;
  @Mock private SkillRepository skillRepository;
  @Mock private RecommendationRepository recommendationRepository;

  @InjectMocks private RefreshRecommendationsForUserUseCase useCase;

  private User createUser() {
    return new User("Teste", "teste@email.com", "Senha123", true);
  }

  private Skill createSkill(String skillId, Categories category, String name, int isEssential) {
    Skill skill = new Skill(category, name, isEssential);
    skill.setId(skillId);
    return skill;
  }

  private Course createCourse(String courseId, String contributorId, List<String> skillIds) {
    Course course =
        new Course(
            true,
            contributorId,
            "Curso " + courseId.substring(0, 6),
            "Iniciante",
            Categories.DEV_WEB,
            "Descrição do curso",
            "https://www.youtube.com/watch?v=abc123XYZ89",
            1,
            skillIds);
    course.setId(courseId);
    return course;
  }

  private ProgressDetails createFinishedProgressDetails(String userId, Course course) {
    String lessonId = new ObjectId().toString();
    Progress progress = new Progress(userId, course.getId(), List.of(lessonId), 1);
    return new ProgressDetails(progress, this.createUser(), course, List.of());
  }

  @Test
  @DisplayName(
      "happy path (execute): ordenar por maior gap, excluir concluídos e ignorar cursos sem skill conhecida")
  void shouldRankRecommendationsByGapAndExcludeFinishedCourses() {
    String userId = new ObjectId().toString();
    String contributorId = new ObjectId().toString();
    String htmlSkillId = new ObjectId().toString();
    String gitSkillId = new ObjectId().toString();
    String javascriptSkillId = new ObjectId().toString();

    Skill htmlSkill = this.createSkill(htmlSkillId, Categories.DEV_WEB, "HTML", 2);
    Skill javascriptSkill =
        this.createSkill(javascriptSkillId, Categories.DEV_WEB, "JavaScript", 3);

    Course finishedCourse =
        this.createCourse(new ObjectId().toString(), contributorId, List.of(javascriptSkillId));
    Course higherGapCourse =
        this.createCourse(new ObjectId().toString(), contributorId, List.of(htmlSkillId));
    Course lowerGapCourse =
        this.createCourse(new ObjectId().toString(), contributorId, List.of(gitSkillId));

    SkillProfile htmlProfile = new SkillProfile(userId, htmlSkillId, 40);

    when(this.userRepository.findByIdOrThrow(userId)).thenReturn(this.createUser());
    when(this.skillProfileRepository.findAllByUserId(userId)).thenReturn(List.of(htmlProfile));
    when(this.progressRepository.findFinishedCourseIdsByUserId(userId))
        .thenReturn(List.of(finishedCourse.getId()));
    when(this.courseRepository.search(
            any(CourseSearchParams.class), eq(MatchType.PERFECT), eq(CombineWith.AND), eq(true)))
        .thenReturn(List.of(finishedCourse, higherGapCourse, lowerGapCourse));
    when(this.skillRepository.findAllById(any(Iterable.class))).thenReturn(List.of(htmlSkill));
    when(this.recommendationRepository.upsertByUserId(userId, List.of(higherGapCourse.getId())))
        .thenReturn(new Recommendation(userId, List.of(higherGapCourse.getId())));

    Recommendation recommendation = this.useCase.execute(userId);

    assertEquals(List.of(higherGapCourse.getId()), recommendation.getCourseIdsAsString());
    verify(this.recommendationRepository).upsertByUserId(userId, List.of(higherGapCourse.getId()));
  }

  @Test
  @DisplayName(
      "functional (execute): retornar vazio sem skillProfile e ignorar skills não relacionadas")
  void shouldReturnEmptyWhenUserDoesNotHaveSkillProfile() {
    String userId = new ObjectId().toString();
    String contributorId = new ObjectId().toString();
    String dockerSkillId = new ObjectId().toString();

    Skill dockerSkill = this.createSkill(dockerSkillId, Categories.CLOUD, "Docker", 2);
    Course validCourse =
        this.createCourse(new ObjectId().toString(), contributorId, List.of(dockerSkillId));

    when(this.userRepository.findByIdOrThrow(userId)).thenReturn(this.createUser());
    when(this.skillProfileRepository.findAllByUserId(userId)).thenReturn(List.of());
    when(this.recommendationRepository.upsertByUserId(userId, List.of()))
        .thenReturn(new Recommendation(userId, List.of()));

    Recommendation recommendation = this.useCase.execute(userId);

    assertTrue(recommendation.getCourseIdsAsString().isEmpty());
  }

  @Test
  @DisplayName(
      "functional (execute): recomendar somente cursos com skills já conhecidas no skillProfile")
  void shouldRecommendOnlyCoursesWithKnownSkills() {
    String userId = new ObjectId().toString();
    String contributorId = new ObjectId().toString();
    String reactSkillId = new ObjectId().toString();
    String dockerSkillId = new ObjectId().toString();
    String unknownSkillId = new ObjectId().toString();

    Skill reactSkill = this.createSkill(reactSkillId, Categories.DEV_WEB, "React", 3);
    Skill dockerSkill = this.createSkill(dockerSkillId, Categories.CLOUD, "Docker", 2);

    Course relatedCourse =
        this.createCourse(
            new ObjectId().toString(), contributorId, List.of(reactSkillId, unknownSkillId));
    Course unrelatedCourse =
        this.createCourse(new ObjectId().toString(), contributorId, List.of(dockerSkillId));

    SkillProfile reactProfile = new SkillProfile(userId, reactSkillId, 20);

    when(this.userRepository.findByIdOrThrow(userId)).thenReturn(this.createUser());
    when(this.skillProfileRepository.findAllByUserId(userId)).thenReturn(List.of(reactProfile));
    when(this.progressRepository.findFinishedCourseIdsByUserId(userId)).thenReturn(List.of());
    when(this.courseRepository.search(
            any(CourseSearchParams.class), eq(MatchType.PERFECT), eq(CombineWith.AND), eq(true)))
        .thenReturn(List.of(relatedCourse, unrelatedCourse));
    when(this.skillRepository.findAllById(any(Iterable.class)))
        .thenReturn(List.of(reactSkill, dockerSkill));
    when(this.recommendationRepository.upsertByUserId(userId, List.of(relatedCourse.getId())))
        .thenReturn(new Recommendation(userId, List.of(relatedCourse.getId())));

    Recommendation recommendation = this.useCase.execute(userId);

    assertEquals(List.of(relatedCourse.getId()), recommendation.getCourseIdsAsString());
  }
}
