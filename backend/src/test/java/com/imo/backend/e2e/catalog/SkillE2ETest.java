package com.imo.backend.e2e.catalog;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.imo.backend.contexts.catalog.course.http.dtos.CategoryDTO;
import com.imo.backend.contexts.catalog.skill.http.dtos.SkillDTO;
import com.imo.backend.e2e.BaseE2ETest;
import io.restassured.http.ContentType;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class SkillE2ETest extends BaseE2ETest {

  @Test
  @DisplayName(
      "happy path (GET /api/skill/{categorySlug}): retorna 200 com lista de skills por categoria")
  void shouldGetSkillsByCategory() {
    String categorySlug = new CategoryDTO("Desenvolvimento web", "desenvolvimento-web").slug();

    List<SkillDTO> skills =
        given()
            .when()
            .get("/api/skill/{categorySlug}", categorySlug)
            .then()
            .statusCode(HttpStatus.OK.value())
            .contentType(ContentType.JSON)
            .extract()
            .jsonPath()
            .getList(".", SkillDTO.class);

    assertFalse(skills.isEmpty());
    assertTrue(skills.stream().allMatch(skill -> skill.category().slug().equals(categorySlug)));
  }
}
