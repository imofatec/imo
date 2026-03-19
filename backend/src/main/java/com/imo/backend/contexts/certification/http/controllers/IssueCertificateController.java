package com.imo.backend.contexts.certification.http.controllers;

import com.imo.backend.contexts.certification.usecases.IssueCertificateUseCase;
import com.imo.backend.contexts.common.MongoDB;
import io.swagger.v3.oas.annotations.Operation;
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
