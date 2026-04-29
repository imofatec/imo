package com.imo.backend.contexts.learning_path.recommendation.http.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Recommendation", description = "Endpoints de recomendação de cursos")
@RequestMapping("/api/recommendation")
public abstract class RecommendationController {}
