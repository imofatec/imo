package com.imo.backend.contexts.certification.http.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.imo.backend.contexts.certification.use_cases.IssueCertificateUseCase;
import com.imo.backend.contexts.common.MongoDB;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
public class IssueCertificateController extends CertificateController {
  private final IssueCertificateUseCase issueCertificateUseCase;

  public IssueCertificateController(IssueCertificateUseCase issueCertificateUseCase) {
    this.issueCertificateUseCase = issueCertificateUseCase;
  }

  @Operation(summary = "Issue certificate by course id")
  @SecurityRequirement(name = "Authorization")
  @GetMapping("/issue/{courseId}")
  public ResponseEntity<byte[]> handle(
      @PathVariable String courseId
  ) {
    MongoDB.validateObjectId(courseId);
    String userId = SecurityContextHolder.getContext().getAuthentication().getName();
    IssueCertificateUseCase.IssueCertificateResult result = this.issueCertificateUseCase.execute(userId, courseId);
    String contentDisposition = String.format("attachment; filename=%s.pdf", result.filename());
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
        .contentType(MediaType.APPLICATION_PDF)
        .body(result.pdf());
  }
}
