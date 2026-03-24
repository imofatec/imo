package com.imo.backend.contexts.journey_tracking.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Journey Tracking - Progress", description = "Endpoints de acompanhamento de progresso")
@RequestMapping("/api/progress")
public abstract class ProgressController {
}
