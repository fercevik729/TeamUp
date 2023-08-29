package com.fercevik.programservice.exceptions;

public class ProgramNotFoundException extends RuntimeException{
    public ProgramNotFoundException(String s) {
        super(s);
    }
}
