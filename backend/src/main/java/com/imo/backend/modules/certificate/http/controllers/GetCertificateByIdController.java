package com.imo.backend.modules.certificate.http.controllers;

import com.imo.backend.modules.certificate.CertificateDetails;
import com.imo.backend.modules.certificate.guards.GetCertificateDetailsByIdGuard;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetCertificateByIdController extends CertificateController {
  private final GetCertificateDetailsByIdGuard getCertificateDetailsByIdGuard;

  public GetCertificateByIdController(GetCertificateDetailsByIdGuard getCertificateDetailsByIdGuard) {
    this.getCertificateDetailsByIdGuard = getCertificateDetailsByIdGuard;
  }

  @Operation(summary = "Get certificate details by id")
  @GetMapping("/details/{id}")
  public ResponseEntity<CertificateDetails> handle(
      @PathVariable
      String id
  ) {
    return ResponseEntity.ok(this.getCertificateDetailsByIdGuard.execute(id));
  }
}
