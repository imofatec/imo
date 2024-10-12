package com.imo.backend.controllers.user.create;

import com.imo.backend.config.token.TokenService;
import com.imo.backend.controllers.user.UserController;
import com.imo.backend.models.certificate.Certificate;
import com.imo.backend.models.certificate.services.CreatePdfService;
import com.imo.backend.models.user.repositories.UserRepository;
import com.imo.backend.models.user.services.update.UpdateUserCertificatesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;

@RestController
public class CertificateController extends UserController {

    private final CreatePdfService createPdfService;
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final UpdateUserCertificatesService updateUserCertificatesService;


    public CertificateController(CreatePdfService createPdfService, UserRepository userRepository, TokenService tokenService, UpdateUserCertificatesService updateUserCertificatesService) {
        this.createPdfService = createPdfService;
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.updateUserCertificatesService = updateUserCertificatesService;
    }

    @Operation(summary = "Get Certificate")
    @SecurityRequirement(name = "Authorization")
    @GetMapping("/create-certificate/{courseId}")
    public ResponseEntity<byte[]> handle(HttpServletRequest request, @PathVariable String courseId) throws Exception {

        var contributor = tokenService.getSub(request.getHeader("Authorization"));
        String userId = contributor.get("id");

        Certificate certificate = updateUserCertificatesService.updateUserCertificates(userId, courseId);

        ByteArrayInputStream pdf = createPdfService.execute(certificate);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=certificate.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf.readAllBytes());
    }
}
