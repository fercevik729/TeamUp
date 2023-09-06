package com.fercevik.programservice.exceptions;

public class ProgramAlreadyExistsException extends RuntimeException{
    public ProgramAlreadyExistsException(String s) {
        super(s);
    }
}
