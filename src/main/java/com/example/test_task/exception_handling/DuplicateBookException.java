package com.example.test_task.exception_handling;

public class DuplicateBookException extends RuntimeException{

    public DuplicateBookException(String message) {
        super(message);
    }
}
