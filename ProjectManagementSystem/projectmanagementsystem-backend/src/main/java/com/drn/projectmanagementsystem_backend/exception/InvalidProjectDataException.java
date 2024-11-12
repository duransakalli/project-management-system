package com.drn.projectmanagementsystem_backend.exception;

public class InvalidProjectDataException extends RuntimeException {
    public InvalidProjectDataException(String message) {
        super(message);
    }
}
