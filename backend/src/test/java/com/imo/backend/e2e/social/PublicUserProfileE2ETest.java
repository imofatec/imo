package com.imo.backend.e2e.social;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

import com.imo.backend.contexts.catalog.course.http.dtos.CourseDetailsDTO;
import com.imo.backend.contexts.catalog.course.value_objects.Categories;
import com.imo.backend.contexts.identity.user.http.dtos.UpdateUserByIdRequest;
import com.imo.backend.contexts.identity.user.http.dtos.UserDTO;
import com.imo.backend.contexts.social.profile.http.dtos.PublicUserProfileDTO;
import com.imo.backend.e2e.BaseE2ETest;
import com.imo.backend.e2e.catalog.helpers.CatalogSkillTestHelper;
import com.imo.backend.e2e.catalog.helpers.CatalogTestHelper;
import com.imo.backend.e2e.catalog.helpers.CatalogTestHelper.TestCourse;
import com.imo.backend.e2e.identity.helpers.IdentityTestHelper;
import com.imo.backend.e2e.identity.helpers.IdentityTestHelper.TestUser;
import com.imo.backend.e2e.journey_tracking.helpers.JourneyTrackingTestHelper;
import com.imo.backend.e2e.social.helpers.SocialTestHelper;
import io.restassured.http.ContentType;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class PublicUserProfileE2ETest extends BaseE2ETest {
  @Test
  @DisplayName(
      "happy path (GET /api/user/public/{id}): retorna perfil público com bio, conquistas e última atividade")
  void shouldReturnPublicUserProfile() {
    String token = IdentityTestHelper.registerAndLogin(TestUser.defaultUser());
    UserDTO currentUser = this.getCurrentUserProfile(token);

    given()
        .header("Authorization", "Bearer " + token)
        .contentType(ContentType.JSON)
        .body(
            new UpdateUserByIdRequest(
                null, null, "Bio pública", null, null, null, null, null, null, null))
        .when()
        .put("/api/user")
        .then()
        .statusCode(HttpStatus.OK.value());

    CourseDetailsDTO courseOne =
        CatalogTestHelper.createCourse(token, this.buildThreeLessonCourse("Curso Perfil 1"));
    CourseDetailsDTO courseTwo =
        CatalogTestHelper.createCourse(token, this.buildThreeLessonCourse("Curso Perfil 2"));
    CourseDetailsDTO courseThree =
        CatalogTestHelper.createCourse(token, this.buildThreeLessonCourse("Curso Perfil 3"));
    CourseDetailsDTO courseFour =
        CatalogTestHelper.createCourse(token, this.buildThreeLessonCourse("Curso Perfil 4"));

    this.watchAllLessons(token, courseOne);
    this.watchAllLessons(token, courseTwo);
    this.watchAllLessons(token, courseThree);
    JourneyTrackingTestHelper.watchLesson(token, courseFour.lessons().getFirst().getId());

    SocialTestHelper.createComment(
        token,
        courseFour.lessons().getFirst().getId(),
        new SocialTestHelper.TestComment("Meu último comentário", null));

    PublicUserProfileDTO publicProfile = this.waitForPublicProfile(currentUser.id(), 3);

    assertEquals(currentUser.name(), publicProfile.name());
    assertEquals("Bio pública", publicProfile.bio());
    assertEquals(currentUser.profilePicturePath(), publicProfile.profilePicturePath());
    assertEquals(currentUser.categoriesOfInterest(), publicProfile.categoriesOfInterest());
    assertEquals(3, publicProfile.highlightedAchievements().size());
    assertTrue(
        publicProfile.highlightedAchievements().stream()
            .map(highlightedAchievement -> highlightedAchievement.key())
            .toList()
            .containsAll(List.of("FIRST_COURSE", "THREE_COURSES", "TEN_LESSONS")));
    assertTrue(
        publicProfile.highlightedAchievements().stream()
            .noneMatch(
                highlightedAchievement -> highlightedAchievement.key().equals("FIRST_LESSON")));

    assertEquals(3, publicProfile.lastActivity().recentCourses().size());
    assertEquals(
        "Curso Perfil 4", publicProfile.lastActivity().recentCourses().get(0).courseName());
    assertEquals(
        "Curso Perfil 3", publicProfile.lastActivity().recentCourses().get(1).courseName());
    assertEquals(1, publicProfile.lastActivity().recentCourses().get(0).watchedLessonsCount());
    assertEquals(3, publicProfile.lastActivity().recentCourses().get(1).watchedLessonsCount());

    assertNotNull(publicProfile.lastActivity().lastComment());
    assertEquals("Meu último comentário", publicProfile.lastActivity().lastComment().content());
    assertEquals("Curso Perfil 4", publicProfile.lastActivity().lastComment().courseName());
  }

  @Test
  @DisplayName("exception (GET /api/user/public/{id}): retorna 404 quando usuário não existe")
  void shouldReturn404WhenPublicUserProfileDoesNotExist() {
    given()
        .when()
        .get("/api/user/public/{id}", "507f1f77bcf86cd799439011")
        .then()
        .statusCode(HttpStatus.NOT_FOUND.value())
        .contentType(ContentType.JSON)
        .body("error", equalTo("NOT_FOUND"));
  }

  private UserDTO getCurrentUserProfile(String token) {
    return given()
        .header("Authorization", "Bearer " + token)
        .when()
        .get("/api/user/profile")
        .then()
        .statusCode(HttpStatus.OK.value())
        .extract()
        .as(UserDTO.class);
  }

  private PublicUserProfileDTO waitForPublicProfile(String userId, int expectedAchievementsCount) {
    return Awaitility.await()
            .atMost(3, TimeUnit.SECONDS)
            .until(
                () ->
                    this.getPublicUserProfile(userId).highlightedAchievements().size()
                        == expectedAchievementsCount,
                equalTo(true))
        ? this.getPublicUserProfile(userId)
        : null;
  }

  private PublicUserProfileDTO getPublicUserProfile(String userId) {
    return given()
        .when()
        .get("/api/user/public/{id}", userId)
        .then()
        .statusCode(HttpStatus.OK.value())
        .contentType(ContentType.JSON)
        .extract()
        .as(PublicUserProfileDTO.class);
  }

  private TestCourse buildThreeLessonCourse(String name) {
    TestCourse baseCourse = TestCourse.defaultCourseWithThreeLessons();

    return new TestCourse(
        name,
        Categories.DEV_WEB,
        "Iniciante",
        "Curso criado para testar perfil público",
        baseCourse.lessons(),
        CatalogSkillTestHelper.getSkillIdsForCategory(Categories.DEV_WEB, 2));
  }

  private void watchAllLessons(String token, CourseDetailsDTO courseDetails) {
    courseDetails
        .lessons()
        .forEach(lesson -> JourneyTrackingTestHelper.watchLesson(token, lesson.getId()));
  }
}
