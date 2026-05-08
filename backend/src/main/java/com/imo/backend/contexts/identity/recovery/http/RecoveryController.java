package com.imo.backend.contexts.identity.recovery.http;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Identity - Recovery", description = "Endpoints de recuperação de senha")
@RequestMapping("/api/recovery")
public abstract class RecoveryController {}
