package com.drn.projectmanagementsystem_backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadTokenException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleBadTokenException(BadTokenException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleUserNotFoundException(UserNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(UserExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleUserExistsException(UserExistsException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleBadCredentialsException(BadCredentialsException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(ProjectNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleProjectNotFoundException(ProjectNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(InvalidProjectDataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleInvalidProjectDataException(InvalidProjectDataException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(UserNotAuthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleUserNotAuthorizedException(UserNotAuthorizedException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(InvitationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleInvitationNotFoundException(InvitationNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(IssueNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleIssueNotFoundException(IssueNotFoundException ex) {
        return ex.getMessage();
    }


}
