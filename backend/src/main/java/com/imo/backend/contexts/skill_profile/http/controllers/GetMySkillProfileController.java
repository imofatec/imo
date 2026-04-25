package com.imo.backend.contexts.skill_profile.http.controllers;

import com.imo.backend.contexts.common.exceptions.ErrorResponseDto;
import com.imo.backend.contexts.identity.user.http.controllers.UserController;
import com.imo.backend.contexts.skill_profile.http.dtos.SkillProfileDTO;
import com.imo.backend.contexts.skill_profile.repositories.SkillProfileRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetMySkillProfileController extends UserController {
  private final SkillProfileRepository skillProfileRepository;

  public GetMySkillProfileController(SkillProfileRepository skillProfileRepository) {
    this.skillProfileRepository = skillProfileRepository;
  }

  @Operation(summary = "Get logged user skill profile")
  @SecurityRequirement(name = "Authorization")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Perfil de skills obtido com sucesso",
            content =
                @Content(
                    array =
                        @ArraySchema(schema = @Schema(implementation = SkillProfileDTO.class)))),
        @ApiResponse(
            responseCode = "401",
            description = "Não autenticado",
            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Skill do perfil não encontrada",
            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
      })
  @GetMapping("/skill-profile")
  public ResponseEntity<List<SkillProfileDTO>> handle() {
    String userId = SecurityContextHolder.getContext().getAuthentication().getName();
    var response =
        this.skillProfileRepository.findAllDetailsByUserId(userId).stream()
            .map(SkillProfileDTO::fromDetails)
            .toList();

    return ResponseEntity.ok(response);
  }
}
