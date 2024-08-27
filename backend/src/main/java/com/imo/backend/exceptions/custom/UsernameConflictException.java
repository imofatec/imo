package com.imo.backend.exceptions.custom;

public class UsernameConflictException extends RuntimeException{
    public UsernameConflictException(){ super("Username já existe");}
}
