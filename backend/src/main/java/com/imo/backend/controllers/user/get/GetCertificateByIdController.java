package com.imo.backend.controllers.user.get;

import com.imo.backend.controllers.user.UserController;
import com.imo.backend.models.certificate.dtos.FormattedCertificate;
import com.imo.backend.models.certificate.services.GetCertificateByIdService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetCertificateByIdController extends UserController {
    private final GetCertificateByIdService getCertificateByIdService;

    public GetCertificateByIdController(GetCertificateByIdService getCertificateByIdService) {
        this.getCertificateByIdService = getCertificateByIdService;
    }

    @Operation(summary = "Get a certificate by id")
    @GetMapping("certificate/{certificateId}")
    public ResponseEntity<FormattedCertificate> handle(@PathVariable String certificateId) {
        var certificate = getCertificateByIdService.execute(certificateId);
        return ResponseEntity.ok(certificate);
    }
}
