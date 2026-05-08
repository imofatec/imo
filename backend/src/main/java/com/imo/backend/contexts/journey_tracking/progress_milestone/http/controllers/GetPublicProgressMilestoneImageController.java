package com.imo.backend.contexts.journey_tracking.progress_milestone.http.controllers;

import com.imo.backend.contexts.journey_tracking.progress_milestone.lib.ProgressMilestoneImageRenderer;
import com.imo.backend.contexts.journey_tracking.progress_milestone.usecases.GetPublicProgressMilestoneUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetPublicProgressMilestoneImageController extends ProgressMilestoneController {
  private final GetPublicProgressMilestoneUseCase getPublicProgressMilestoneUseCase;
  private final ProgressMilestoneImageRenderer progressMilestoneImageRenderer;

  public GetPublicProgressMilestoneImageController(
      GetPublicProgressMilestoneUseCase getPublicProgressMilestoneUseCase,
      ProgressMilestoneImageRenderer progressMilestoneImageRenderer) {
    this.getPublicProgressMilestoneUseCase = getPublicProgressMilestoneUseCase;
    this.progressMilestoneImageRenderer = progressMilestoneImageRenderer;
  }

  @Operation(summary = "Render public progress milestone as PNG")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Imagem do marco de progresso gerada com sucesso",
            content =
                @Content(
                    mediaType = "image/png",
                    schema = @Schema(type = "string", format = "binary"))),
        @ApiResponse(
            responseCode = "404",
            description = "Marco de progresso não encontrado",
            content =
                @Content(
                    schema =
                        @Schema(
                            implementation =
                                com.imo.backend.contexts.common.exceptions.ErrorResponseDto.class)))
      })
  @GetMapping("/public/{publicCode}/image.png")
  public ResponseEntity<byte[]> handle(@PathVariable String publicCode) {
    var milestone = this.getPublicProgressMilestoneUseCase.execute(publicCode);
    byte[] image = this.progressMilestoneImageRenderer.execute(milestone);

    return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(image);
  }
}
