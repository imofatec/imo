package com.imo.backend.contexts.identity.commands;

public record CreateUserCommand(
    String name,
    String email,
    String password,
    String confPassword
) {
}
