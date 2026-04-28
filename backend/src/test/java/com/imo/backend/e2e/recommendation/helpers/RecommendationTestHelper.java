package com.imo.backend.e2e.recommendation.helpers;

import static io.restassured.RestAssured.given;
import static org.awaitility.Awaitility.await;

import com.imo.backend.contexts.catalog.course.http.dtos.CourseDTO;
import com.imo.backend.contexts.recommendation.Recommendation;
import com.imo.backend.contexts.recommendation.repositories.RecommendationRepository;
import io.restassured.http.ContentType;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public final class RecommendationTestHelper {
  private RecommendationTestHelper() {}

  public static Recommendation waitForRecommendation(
      RecommendationRepository recommendationRepository, String userId, int expectedCount) {
    AtomicReference<Recommendation> recommendationRef = new AtomicReference<>();

    await()
        .atMost(Duration.ofSeconds(2))
        .pollInterval(Duration.ofMillis(100))
        .until(
            () -> {
              Recommendation recommendation =
                  recommendationRepository.findByUserId(userId).orElse(null);
              recommendationRef.set(recommendation);
              return recommendation != null
                  && recommendation.getCourseIdsAsString().size() == expectedCount;
            });

    return recommendationRef.get();
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
