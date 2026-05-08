package com.imo.backend.e2e.recognition;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

import com.imo.backend.contexts.catalog.course.http.dtos.CourseDetailsDTO;
import com.imo.backend.contexts.recognition.http.dtos.AchievementCardDTO;
import com.imo.backend.contexts.recognition.http.dtos.ProfileAchievementsDTO;
import com.imo.backend.contexts.recognition.repositories.StaticAchievementRepository.StaticAchievementsKeys;
import com.imo.backend.e2e.BaseE2ETest;
import com.imo.backend.e2e.catalog.helpers.CatalogTestHelper;
import com.imo.backend.e2e.catalog.helpers.CatalogTestHelper.TestCourse;
import com.imo.backend.e2e.identity.helpers.IdentityTestHelper;
import com.imo.backend.e2e.identity.helpers.IdentityTestHelper.TestUser;
import com.imo.backend.e2e.journey_tracking.helpers.JourneyTrackingTestHelper;
import io.restassured.http.ContentType;
import java.util.concurrent.TimeUnit;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class AchievementsE2ETest extends BaseE2ETest {
  @Test
  @DisplayName(
      "happy path (GET /api/user/profile/achievements): retorna achievements completos do perfil")
  void shouldReturnProfileAchievements() {
    String token = IdentityTestHelper.registerAndLogin(TestUser.defaultUser());
    CourseDetailsDTO courseDetails =
        CatalogTestHelper.createCourse(token, TestCourse.defaultCourseWithThreeLessons());

    courseDetails
        .lessons()
        .forEach(lesson -> JourneyTrackingTestHelper.watchLesson(token, lesson.getId()));

    ProfileAchievementsDTO achievements = this.waitForAchievements(token, 2);

    assertEquals(8, achievements.total());
    assertEquals(2, achievements.unlockedCount());
    assertEquals(6, achievements.lockedCount());

    AchievementCardDTO firstLesson =
        this.findByKey(achievements, StaticAchievementsKeys.FIRST_LESSON);
    assertNotNull(firstLesson.unlockedAt());
    assertEquals(
        this.baseUrl() + "/images/achievements/unlocked/first-lesson.png", firstLesson.imageUrl());
    assertEquals(3L, firstLesson.currentValue());
    assertEquals(1, firstLesson.targetValue());
    assertEquals(100, firstLesson.progressPercentage());

    AchievementCardDTO firstCourse =
        this.findByKey(achievements, StaticAchievementsKeys.FIRST_COURSE);
    assertNotNull(firstCourse.unlockedAt());
    assertEquals(
        this.baseUrl() + "/images/achievements/unlocked/first-course.png", firstCourse.imageUrl());
    assertEquals(1L, firstCourse.currentValue());
    assertEquals(1, firstCourse.targetValue());
    assertEquals(100, firstCourse.progressPercentage());

    AchievementCardDTO tenCourses =
        this.findByKey(achievements, StaticAchievementsKeys.TEN_COURSES);
    assertNull(tenCourses.unlockedAt());
    assertEquals(this.baseUrl() + "/images/achievements/locked/default.png", tenCourses.imageUrl());
    assertEquals(1L, tenCourses.currentValue());
    assertEquals(10, tenCourses.targetValue());
    assertEquals(10, tenCourses.progressPercentage());

    AchievementCardDTO tenLessons =
        this.findByKey(achievements, StaticAchievementsKeys.TEN_LESSONS);
    assertNull(tenLessons.unlockedAt());
    assertEquals(this.baseUrl() + "/images/achievements/locked/default.png", tenLessons.imageUrl());
    assertEquals(3L, tenLessons.currentValue());
    assertEquals(10, tenLessons.targetValue());
    assertEquals(30, tenLessons.progressPercentage());

    AchievementCardDTO threeCourses =
        this.findByKey(achievements, StaticAchievementsKeys.THREE_COURSES);
    assertNull(threeCourses.unlockedAt());
    assertEquals(
        this.baseUrl() + "/images/achievements/locked/default.png", threeCourses.imageUrl());
    assertEquals(1L, threeCourses.currentValue());
    assertEquals(3, threeCourses.targetValue());
    assertEquals(33, threeCourses.progressPercentage());

    AchievementCardDTO fiftyLessons =
        this.findByKey(achievements, StaticAchievementsKeys.FIFTY_LESSONS);
    assertNull(fiftyLessons.unlockedAt());
    assertEquals(
        this.baseUrl() + "/images/achievements/locked/default.png", fiftyLessons.imageUrl());
    assertEquals(3L, fiftyLessons.currentValue());
    assertEquals(50, fiftyLessons.targetValue());
    assertEquals(6, fiftyLessons.progressPercentage());
  }

  @Test
  @DisplayName("exception (GET /api/user/profile/achievements): retorna 401 quando não autenticado")
  void shouldReturn401WhenGettingProfileAchievementsNotAuthenticated() {
    given()
        .when()
        .get("/api/user/profile/achievements")
        .then()
        .statusCode(HttpStatus.UNAUTHORIZED.value())
        .contentType(ContentType.JSON)
        .body("error", equalTo("UNAUTHORIZED"));
  }

  private ProfileAchievementsDTO waitForAchievements(String token, int expectedUnlockedCount) {
    return Awaitility.await()
            .atMost(3, TimeUnit.SECONDS)
            .until(
                () -> this.getProfileAchievements(token).unlockedCount() == expectedUnlockedCount,
                equalTo(true))
        ? this.getProfileAchievements(token)
        : null;
  }

  private ProfileAchievementsDTO getProfileAchievements(String token) {
    return given()
        .header("Authorization", "Bearer " + token)
        .when()
        .get("/api/user/profile/achievements")
        .then()
        .statusCode(HttpStatus.OK.value())
        .contentType(ContentType.JSON)
        .extract()
        .as(ProfileAchievementsDTO.class);
  }

  private AchievementCardDTO findByKey(
      ProfileAchievementsDTO achievements, StaticAchievementsKeys achievementKey) {
    return achievements.items().stream()
        .filter(item -> item.key().equals(achievementKey.name()))
        .findFirst()
        .orElseThrow();
  }
}
