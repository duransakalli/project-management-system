package com.drn.projectmanagementsystem_backend.exception;

public class IssueNotFoundException extends RuntimeException{
    public IssueNotFoundException(String message) {
        super(message);
    }
}
