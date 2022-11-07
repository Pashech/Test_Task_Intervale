package com.example.test_task.exception_handling;

public class NoAnyBookException extends RuntimeException {

    public NoAnyBookException(String message) {
        super(message);
    }
}
