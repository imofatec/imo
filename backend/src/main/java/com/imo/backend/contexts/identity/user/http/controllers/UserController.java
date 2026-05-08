package com.imo.backend.contexts.identity.user.http.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Identity - User", description = "Endpoints de gerenciamento de usuários")
@RequestMapping("/api/user")
public abstract class UserController {}
