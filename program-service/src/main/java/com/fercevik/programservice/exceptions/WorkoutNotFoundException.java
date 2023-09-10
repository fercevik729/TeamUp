package com.fercevik.programservice.exceptions;

public class WorkoutNotFoundException extends RuntimeException{
    public WorkoutNotFoundException(String s) {
        super(s);
    }
}
