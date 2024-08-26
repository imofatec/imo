package com.imo.backend.exceptions.custom;

public class WrongPasswordException extends RuntimeException {
    public WrongPasswordException() {
        super("Senha inválida");
    }
}
