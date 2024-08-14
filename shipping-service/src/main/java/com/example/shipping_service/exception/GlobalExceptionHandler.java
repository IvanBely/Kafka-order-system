package com.example.shipping_service.exception;

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
    public ResponseEntity<?> handleGlobalException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(new ErrorDetails("An error has occurred"), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<ErrorDetails> handleJsonProcessingException(JsonProcessingException ex) {
        return new ResponseEntity<>(new ErrorDetails("Error processing JSON: " + ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(InvalidOrderStatusException.class)
    public ResponseEntity<ErrorDetails> handleJsonProcessingException(InvalidOrderStatusException ex) {
        return new ResponseEntity<>(new ErrorDetails("Error processing JSON: " + ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(OrderNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(PackagingException.class)
    public ResponseEntity<String> handleUserNotFoundException(PackagingException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
    @Data
    private static class ErrorDetails {
        private String message;

        public ErrorDetails(String message) {
            this.message = message;
        }
    }
}
