package com.imo.backend.contexts.learning_path.skill_profile.http.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(
    name = "Learning path - Skill Profile",
    description = "Endpoints de perfil de skills do usuário")
@RequestMapping("/api/skill-profile")
public abstract class SkillProfileController {}
