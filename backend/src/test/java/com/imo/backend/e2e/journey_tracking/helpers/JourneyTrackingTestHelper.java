package com.imo.backend.e2e.journey_tracking.helpers;

import com.imo.backend.contexts.journey_tracking.controllers.dtos.ProgressDTO;

import static io.restassured.RestAssured.given;

public final class JourneyTrackingTestHelper {

  public static ProgressDTO watchLesson(String token, String lessonId) {
    return given()
        .header("Authorization", "Bearer " + token)
        .when()
        .put("/api/progress/{lessonId}", lessonId)
        .then()
        .extract()
        .as(ProgressDTO.class);
  }
}
