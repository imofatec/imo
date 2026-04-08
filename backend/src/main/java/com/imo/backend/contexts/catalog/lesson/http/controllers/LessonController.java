package com.imo.backend.contexts.catalog.lesson.http.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Catalog - Lesson", description = "Endpoints de gerenciamento de aulas")
@RequestMapping("/api/lesson")
public abstract class LessonController {}
