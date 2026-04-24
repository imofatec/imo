package com.imo.backend.contexts.catalog.skill.http.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Catalog - Skill", description = "Endpoints de gerenciamento de skills")
@RequestMapping("/api/skill")
public abstract class SkillController {}
