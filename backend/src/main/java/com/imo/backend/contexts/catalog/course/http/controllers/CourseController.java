package com.imo.backend.contexts.catalog.course.http.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;


@Tag(name = "Catalog - Course", description = "Endpoints de gerenciamento de cursos")
@RequestMapping("/api/course")
public abstract class CourseController {
}
