package com.imo.backend.e2e.recommendation.helpers;

import static io.restassured.RestAssured.given;

import com.imo.backend.contexts.catalog.course.http.dtos.CourseDTO;
import com.imo.backend.contexts.recommendation.Recommendation;
import com.imo.backend.contexts.recommendation.repositories.RecommendationRepository;
import io.restassured.http.ContentType;
import java.util.List;

public final class RecommendationTestHelper {
  private RecommendationTestHelper() {}

  public static Recommendation waitForRecommendation(
      RecommendationRepository recommendationRepository, String userId, int expectedCount)
      throws InterruptedException {
    for (int attempt = 0; attempt < 20; attempt++) {
      Recommendation recommendation = recommendationRepository.findByUserId(userId).orElse(null);

      if (recommendation != null && recommendation.getCourseIdsAsString().size() == expectedCount) {
        return recommendation;
      }

      Thread.sleep(100);
    }

    return recommendationRepository.findByUserId(userId).orElse(null);
  }

  public static List<CourseDTO> getMyRecommendations(String token) {
    return given()
        .header("Authorization", "Bearer " + token)
        .when()
        .get("/api/recommendation/me")
        .then()
        .statusCode(200)
        .contentType(ContentType.JSON)
        .extract()
        .jsonPath()
        .getList(".", CourseDTO.class);
  }
}
