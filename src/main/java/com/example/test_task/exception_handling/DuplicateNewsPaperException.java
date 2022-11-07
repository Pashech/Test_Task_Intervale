package com.example.test_task.exception_handling;

public class DuplicateNewsPaperException extends RuntimeException{

    public DuplicateNewsPaperException(String message) {
        super(message);
    }
}
