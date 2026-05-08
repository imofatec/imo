package com.imo.backend.contexts.social.comment.http.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Social - Comment", description = "Endpoints de gerenciamento de comentários")
@RequestMapping("/api/comment")
public abstract class CommentController {}
