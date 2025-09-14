package com.imo.backend.modules.lesson.http.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Lesson controller")
@RequestMapping("/api/lesson")
public abstract class LessonController {
}
