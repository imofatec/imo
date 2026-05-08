package com.imo.backend.contexts.social.profile.http.controllers;

import com.imo.backend.contexts.common.MongoDB;
import com.imo.backend.contexts.identity.user.http.controllers.UserController;
import com.imo.backend.contexts.social.profile.http.dtos.PublicUserProfileDTO;
import com.imo.backend.contexts.social.profile.usecases.GetPublicUserProfileUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class GetPublicUserProfileController extends UserController {
  private final GetPublicUserProfileUseCase getPublicUserProfileUseCase;

  public GetPublicUserProfileController(GetPublicUserProfileUseCase getPublicUserProfileUseCase) {
    this.getPublicUserProfileUseCase = getPublicUserProfileUseCase;
  }

  @Operation(summary = "Get public user profile")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Perfil público obtido com sucesso",
            content = @Content(schema = @Schema(implementation = PublicUserProfileDTO.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Usuário não encontrado",
            content =
                @Content(
                    schema =
                        @Schema(
                            implementation =
                                com.imo.backend.contexts.common.exceptions.ErrorResponseDto.class)))
      })
  @GetMapping("/public/{id}")
  public ResponseEntity<PublicUserProfileDTO> handle(@PathVariable String id) {
    MongoDB.validateObjectId(id);
    String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();

    return ResponseEntity.ok(this.getPublicUserProfileUseCase.execute(id, baseUrl));
  }
}
