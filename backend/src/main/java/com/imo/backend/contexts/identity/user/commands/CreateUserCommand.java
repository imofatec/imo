package com.imo.backend.contexts.identity.user.commands;

public record CreateUserCommand(String name, String email, String password, String confPassword) {}
