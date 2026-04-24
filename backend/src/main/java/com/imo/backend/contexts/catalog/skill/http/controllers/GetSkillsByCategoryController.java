package com.imo.backend.contexts.catalog.skill.http.controllers;

import com.imo.backend.contexts.catalog.skill.http.dtos.SkillDTO;
import com.imo.backend.contexts.catalog.skill.repositories.SkillRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetSkillsByCategoryController extends SkillController {
  private final SkillRepository skillRepository;

  public GetSkillsByCategoryController(SkillRepository skillRepository) {
    this.skillRepository = skillRepository;
  }

  @Operation(summary = "Get skills by category")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Skills obtidas com sucesso",
            content =
                @Content(array = @ArraySchema(schema = @Schema(implementation = SkillDTO.class))))
      })
  @GetMapping
  public ResponseEntity<List<SkillDTO>> handle(
      @Parameter(description = "Category slug", example = "desenvolvimento-web") @RequestParam
          String categorySlug) {
    var response =
        this.skillRepository.findAllByCategorySlug(categorySlug).stream()
            .map(SkillDTO::fromEntity)
            .toList();

    return ResponseEntity.ok(response);
  }
}
