package com.imo.backend.e2e.skill_profile;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.imo.backend.contexts.catalog.course.http.dtos.CourseDetailsDTO;
import com.imo.backend.contexts.identity.user.repositories.UserRepository;
import com.imo.backend.contexts.skill_profile.http.dtos.SkillProfileDTO;
import com.imo.backend.contexts.skill_profile.repositories.SkillProfileRepository;
import com.imo.backend.e2e.BaseE2ETest;
import com.imo.backend.e2e.catalog.helpers.CatalogTestHelper;
import com.imo.backend.e2e.catalog.helpers.CatalogTestHelper.TestCourse;
import com.imo.backend.e2e.identity.helpers.IdentityTestHelper;
import com.imo.backend.e2e.identity.helpers.IdentityTestHelper.TestUser;
import com.imo.backend.e2e.journey_tracking.helpers.JourneyTrackingTestHelper;
import com.imo.backend.e2e.skill_profile.helpers.SkillProfileTestHelper;
import io.restassured.http.ContentType;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

class SkillProfileE2ETest extends BaseE2ETest {
  @Autowired private UserRepository userRepository;

  @Autowired private SkillProfileRepository skillProfileRepository;

  @Test
  @DisplayName(
      "happy path (GET /api/skill-profile/me): retorna 200 com perfil de skills do usuário autenticado")
  void shouldGetMySkillProfile() {
    TestUser user = TestUser.defaultUser();
    String token = IdentityTestHelper.registerAndLogin(user);
    CourseDetailsDTO courseDetails =
        CatalogTestHelper.createCourse(token, TestCourse.defaultCourse());
    String lessonId = courseDetails.lessons().getFirst().getId();
    String userId = this.userRepository.findByEmail(user.email()).orElseThrow().getId();

    JourneyTrackingTestHelper.watchLesson(token, lessonId);
    SkillProfileTestHelper.waitForSkillProfiles(
        this.skillProfileRepository, userId, courseDetails.course().skillIds().size());

    List<SkillProfileDTO> skillProfiles =
        given()
            .header("Authorization", "Bearer " + token)
            .when()
            .get("/api/skill-profile/me")
            .then()
            .statusCode(HttpStatus.OK.value())
            .contentType(ContentType.JSON)
            .extract()
            .jsonPath()
            .getList(".", SkillProfileDTO.class);

    assertEquals(courseDetails.course().skillIds().size(), skillProfiles.size());
    assertTrue(
        skillProfiles.stream().allMatch(skillProfile -> skillProfile.userId().equals(userId)));
    assertTrue(
        skillProfiles.stream()
            .allMatch(
                skillProfile ->
                    courseDetails.course().skillIds().contains(skillProfile.skill().id())));
    assertTrue(skillProfiles.stream().allMatch(skillProfile -> skillProfile.coverage() > 0));
  }

  @Test
  @DisplayName("exception (GET /api/skill-profile/me): retorna 401 quando não autenticado")
  void shouldReturn401WhenGettingMySkillProfileNotAuthenticated() {
    given()
        .when()
        .get("/api/skill-profile/me")
        .then()
        .statusCode(HttpStatus.UNAUTHORIZED.value())
        .contentType(ContentType.JSON)
        .body("error", equalTo("UNAUTHORIZED"));
  }
}
