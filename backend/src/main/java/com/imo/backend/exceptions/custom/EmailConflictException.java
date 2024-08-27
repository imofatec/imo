package com.imo.backend.exceptions.custom;

public class EmailConflictException extends RuntimeException {
    public EmailConflictException(){super("Email já existe");}
}
