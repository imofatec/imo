package com.imo.backend.contexts.certification.http.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.imo.backend.contexts.certification.http.dtos.CertificateDTO;
import com.imo.backend.contexts.certification.repositories.CertificateRepository;
import com.imo.backend.contexts.common.MongoDB;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
public class GetCertificateByIdController extends CertificateController {
  private final CertificateRepository certificateRepository;

  public GetCertificateByIdController(CertificateRepository certificateRepository) {
    this.certificateRepository = certificateRepository;
  }

  @Operation(summary = "Get certificate details by id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Certificado encontrado",
          content = @Content(schema = @Schema(implementation = CertificateDTO.class))),
      @ApiResponse(responseCode = "404", description = "Certificado não encontrado",
          content = @Content(schema = @Schema(implementation = com.imo.backend.contexts.common.exceptions.ErrorResponseDto.class)))
  })
  @GetMapping("/details/{id}")
  public ResponseEntity<CertificateDTO> handle(
      @PathVariable
      String id
  ) {
    MongoDB.validateObjectId(id);
    return ResponseEntity.ok(
        CertificateDTO.fromCertificateDetails(this.certificateRepository.findByIdOrThrow(id))
    );
  }
}
