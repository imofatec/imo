package com.imo.backend.exceptions.custom;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PasswordNotMatchException extends RuntimeException {

    public PasswordNotMatchException() {
        super("As senhas nao coincidem");
    }
}
