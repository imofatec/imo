package com.imo.backend.unit.skill_profile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.imo.backend.contexts.catalog.course.Course;
import com.imo.backend.contexts.catalog.course.repositories.CourseRepository;
import com.imo.backend.contexts.catalog.course.value_objects.Categories;
import com.imo.backend.contexts.catalog.skill.Skill;
import com.imo.backend.contexts.catalog.skill.repositories.SkillRepository;
import com.imo.backend.contexts.identity.user.User;
import com.imo.backend.contexts.identity.user.repositories.UserRepository;
import com.imo.backend.contexts.skill_profile.SkillProfile;
import com.imo.backend.contexts.skill_profile.events.SkillProfileUpdatedEvent;
import com.imo.backend.contexts.skill_profile.lib.SkillCoverageCalculator;
import com.imo.backend.contexts.skill_profile.repositories.SkillProfileRepository;
import com.imo.backend.contexts.skill_profile.usecases.UpdateSkillProfileOnCourseFinishedUseCase;
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
class UpdateSkillProfileOnCourseFinishedUseCaseTest {
  @Mock private UserRepository userRepository;
  @Mock private CourseRepository courseRepository;
  @Mock private SkillRepository skillRepository;
  @Mock private SkillProfileRepository skillProfileRepository;
  @Mock private SkillCoverageCalculator skillCoverageCalculator;
  @Mock private ApplicationEventPublisher applicationEventPublisher;

  @InjectMocks private UpdateSkillProfileOnCourseFinishedUseCase useCase;

  @Test
  @DisplayName("happy path (execute): publicar evento após atualizar o skill profile")
  void shouldPublishEventAfterUpdatingSkillProfile() {
    String userId = new ObjectId().toString();
    String courseId = new ObjectId().toString();
    String contributorId = new ObjectId().toString();
    String skillId = new ObjectId().toString();

    User user = new User("Teste", "teste@email.com", "Senha123", true);
    Skill skill = new Skill(Categories.DEV_WEB, "HTML", 2);
    skill.setId(skillId);

    Course course =
        new Course(
            true,
            contributorId,
            "Curso de HTML",
            "Iniciante",
            Categories.DEV_WEB,
            "Descrição do curso",
            "https://www.youtube.com/watch?v=abc123XYZ89",
            1,
            List.of(skillId));
    course.setId(courseId);

    SkillProfile skillProfile = new SkillProfile(userId, skillId, 0);

    when(this.userRepository.findByIdOrThrow(userId)).thenReturn(user);
    when(this.courseRepository.findByIdOrThrow(courseId)).thenReturn(course);
    when(this.skillRepository.findAllById(List.of(skillId))).thenReturn(List.of(skill));
    when(this.skillProfileRepository.findByUserIdAndSkillId(userId, skillId))
        .thenReturn(Optional.of(skillProfile));
    when(this.skillCoverageCalculator.calculateNextCoverage(0, 2)).thenReturn(18);
    when(this.skillProfileRepository.saveAll(any()))
        .thenAnswer(invocation -> invocation.getArgument(0));

    List<SkillProfile> updatedSkillProfiles = this.useCase.execute(userId, courseId);

    assertEquals(1, updatedSkillProfiles.size());
    assertEquals(18, updatedSkillProfiles.getFirst().getCoverage());
    verify(this.applicationEventPublisher)
        .publishEvent(
            argThat(
                (Object event) ->
                    event instanceof SkillProfileUpdatedEvent skillProfileUpdatedEvent
                        && skillProfileUpdatedEvent.userId().equals(userId)));
  }
}
