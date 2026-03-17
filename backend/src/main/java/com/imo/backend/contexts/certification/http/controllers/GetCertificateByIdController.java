package com.imo.backend.contexts.certification.http.controllers;

import com.imo.backend.contexts.certification.CertificateDetails;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.imo.backend.contexts.certification.repositories.CertificateRepository;
import com.imo.backend.contexts.common.MongoDB;

@RestController
public class GetCertificateByIdController extends CertificateController {
  private final CertificateRepository certificateRepository;

  public GetCertificateByIdController(CertificateRepository certificateRepository) {
    this.certificateRepository = certificateRepository;
  }

  @Operation(summary = "Get certificate details by id")
  @GetMapping("/details/{id}")
  public ResponseEntity<CertificateDetails> handle(
      @PathVariable
      String id
  ) {
    MongoDB.validateObjectId(id);
    return ResponseEntity.ok(this.certificateRepository.findByIdOrThrow(id));
  }
}
