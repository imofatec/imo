package com.imo.backend.contexts.journey_tracking.progress_milestone.http.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(
    name = "Journey Tracking - Progress Milestones",
    description = "Endpoints de compartilhamento de marcos de progresso")
@RequestMapping("/api/progress/milestones")
public abstract class ProgressMilestoneController {}
