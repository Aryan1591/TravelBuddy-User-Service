package com.travelbuddy.user.exception;

public class PasswordMismatchException extends RuntimeException{
    public PasswordMismatchException(String msg) {
        super(msg);
    }
}
