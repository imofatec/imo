package com.imo.backend.contexts.certification.http.controllers;

import com.imo.backend.contexts.certification.usecases.IssueCertificateUseCase;
import com.imo.backend.contexts.common.MongoDB;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IssueCertificateController extends CertificateController {
  private final IssueCertificateUseCase issueCertificateUseCase;

  public IssueCertificateController(IssueCertificateUseCase issueCertificateUseCase) {
    this.issueCertificateUseCase = issueCertificateUseCase;
  }

  @Operation(summary = "Issue certificate by course id")
  @SecurityRequirement(name = "Authorization")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Certificado emitido com sucesso",
          content = @Content(mediaType = "application/pdf", schema = @Schema(type = "string", format = "binary"))),
      @ApiResponse(responseCode = "401", description = "Não autenticado",
          content = @Content(schema = @Schema(implementation = com.imo.backend.contexts.common.exceptions.ErrorResponseDto.class))),
      @ApiResponse(responseCode = "403", description = "Curso não finalizado",
          content = @Content(schema = @Schema(implementation = com.imo.backend.contexts.common.exceptions.ErrorResponseDto.class))),
      @ApiResponse(responseCode = "404", description = "Curso não encontrado ou usuário não completou o curso",
          content = @Content(schema = @Schema(implementation = com.imo.backend.contexts.common.exceptions.ErrorResponseDto.class)))
  })
  @GetMapping("/issue/{courseId}")
  public ResponseEntity<byte[]> handle(
      @PathVariable
      String courseId, HttpServletRequest request
  ) {
    MongoDB.validateObjectId(courseId);
    HttpHeaders headers = new HttpHeaders();
    String userId = SecurityContextHolder.getContext().getAuthentication().getName();
    byte[] pdf = this.issueCertificateUseCase.execute(userId, courseId, headers);
    return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(pdf);
  }
}
