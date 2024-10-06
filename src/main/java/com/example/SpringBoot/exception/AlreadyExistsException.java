package com.example.SpringBoot.exception;

public class AlreadyExistsException extends RuntimeException {
    public AlreadyExistsException(String message ) {
        super(message);
    }
}
