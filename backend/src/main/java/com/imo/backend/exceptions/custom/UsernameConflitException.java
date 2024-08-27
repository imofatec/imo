package com.imo.backend.exceptions.custom;

public class UsernameConflitException extends RuntimeException{
    public UsernameConflitException(){ super("Username já existe");}
}
