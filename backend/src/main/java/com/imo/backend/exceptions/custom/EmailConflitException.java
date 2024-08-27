package com.imo.backend.exceptions.custom;

public class EmailConflitException extends RuntimeException {
    public EmailConflitException(){super("Email já existe");}
}
