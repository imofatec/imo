package com.imo.backend.contexts.recognition.http;

import com.imo.backend.contexts.identity.user.http.controllers.UserController;
import com.imo.backend.contexts.recognition.http.dtos.ProfileAchievementsDTO;
import com.imo.backend.contexts.recognition.usecases.GetProfileAchievementsUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetProfileAchievementsController extends UserController {
  private final GetProfileAchievementsUseCase getProfileAchievementsUseCase;

  public GetProfileAchievementsController(
      GetProfileAchievementsUseCase getProfileAchievementsUseCase) {
    this.getProfileAchievementsUseCase = getProfileAchievementsUseCase;
  }

  @Operation(summary = "Get profile achievements")
  @SecurityRequirement(name = "Authorization")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Achievements do perfil obtidos com sucesso",
            content = @Content(schema = @Schema(implementation = ProfileAchievementsDTO.class))),
        @ApiResponse(
            responseCode = "401",
            description = "Não autenticado",
            content =
                @Content(
                    schema =
                        @Schema(
                            implementation =
                                com.imo.backend.contexts.common.exceptions.ErrorResponseDto.class)))
      })
  @GetMapping("/profile/achievements")
  public ResponseEntity<ProfileAchievementsDTO> handle() {
    String userId = SecurityContextHolder.getContext().getAuthentication().getName();

    return ResponseEntity.ok(this.getProfileAchievementsUseCase.execute(userId));
  }
}
