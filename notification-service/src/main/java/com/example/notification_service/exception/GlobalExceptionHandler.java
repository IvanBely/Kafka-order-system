package com.example.notification_service.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(new ErrorDetails("An error has occurred"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<ErrorDetails> handleJsonProcessingException(JsonProcessingException ex) {
        return new ResponseEntity<>(new ErrorDetails("Error processing JSON: " + ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NotificationException.class)
    public ResponseEntity<ErrorDetails> handleJsonProcessingException(NotificationException ex) {
        return new ResponseEntity<>(new ErrorDetails("Error processing JSON: " + ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
    @Data
    private static class ErrorDetails {
        private String message;

        public ErrorDetails(String message) {
            this.message = message;
        }
    }
}