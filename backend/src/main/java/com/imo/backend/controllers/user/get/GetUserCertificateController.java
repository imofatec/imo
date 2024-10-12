package com.imo.backend.controllers.user.get;

import com.imo.backend.config.token.TokenService;
import com.imo.backend.controllers.user.UserController;
import com.imo.backend.models.certificate.Certificate;
import com.imo.backend.models.certificate.services.CreateCertificatePdfService;
import com.imo.backend.models.user.services.update.UpdateUserCertificatesService;
import com.imo.backend.lib.FormatDateTime;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class GetUserCertificateController extends UserController {

    private final TokenService tokenService;

    private final UpdateUserCertificatesService updateUserCertificatesService;

    private final CreateCertificatePdfService createCertificatePdfService;

    public GetUserCertificateController(
            TokenService tokenService,
            UpdateUserCertificatesService updateUserCertificatesService,
            CreateCertificatePdfService createCertificatePdfService
    ) {
        this.tokenService = tokenService;
        this.updateUserCertificatesService = updateUserCertificatesService;
        this.createCertificatePdfService = createCertificatePdfService;
    }

    @Operation(summary = "Get certificate from a course")
    @SecurityRequirement(name = "Authorization")
    @GetMapping("/get-certificate/{courseId}")
    public ResponseEntity<byte[]> handle(
            HttpServletRequest request,
            @PathVariable String courseId
    ) {

        var loggedUser = tokenService.getSub(request.getHeader("Authorization"));
        String userId = loggedUser.get("id");

        Certificate certificate = updateUserCertificatesService.execute(courseId, userId);

        byte[] pdf = createCertificatePdfService.execute(certificate);

        HttpHeaders headers = createHeaders(certificate);

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

    private static HttpHeaders createHeaders(Certificate certificate) {
        var issuedAt = FormatDateTime.toDate(certificate.getIssuedAt());

        var filename = String.format("%s-%s-%s",
                certificate.getUsername().toUpperCase(),
                certificate.getCourseSlug().toUpperCase(),
                issuedAt
        );

        var headerValue = String.format("attachment; filename=%s.pdf", filename);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, headerValue);
        return headers;
    }
}
