package com.user.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandle {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException ex){
        Map<String,Object> error = new HashMap<>();
        error.put("timestammp", LocalDateTime.now());
        error.put("message",ex.getMessage());
        error.put("status", HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }
}
