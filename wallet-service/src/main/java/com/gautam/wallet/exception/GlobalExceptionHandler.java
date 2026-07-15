package com.gautam.wallet.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            InsufficientBalanceException.class,
            IllegalArgumentException.class
    })
    public ResponseEntity<Map<String, Object>> handleException(Exception ex) {

        Map<String, Object> response = new HashMap<>();

        response.put("status", 400);
        response.put("message", ex.getMessage());

        return ResponseEntity.badRequest().body(response);
    }
}