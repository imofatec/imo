package com.imo.backend.contexts.certification.http.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Certification - Certificate", description = "Endpoints de gerenciamento de certificados")
@RequestMapping("/api/certificate")
public abstract class CertificateController {
}
