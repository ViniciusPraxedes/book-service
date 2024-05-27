package com.example.bookservice.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<?> handleIllegalStateException(IllegalStateException exception, WebRequest request){
        String requestUri = ((ServletWebRequest)request).getRequest().getRequestURI().toString();

        ExceptionMessage exceptionMessage = new ExceptionMessage(exception.getMessage(), requestUri);

        return new ResponseEntity<>(exceptionMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException exception, WebRequest request){
        String requestUri = ((ServletWebRequest)request).getRequest().getRequestURI().toString();

        ExceptionMessage exceptionMessage = new ExceptionMessage(exception.getMessage(), requestUri);

        return new ResponseEntity<>(exceptionMessage, new HttpHeaders(), HttpStatus.NOT_FOUND);

    }



}