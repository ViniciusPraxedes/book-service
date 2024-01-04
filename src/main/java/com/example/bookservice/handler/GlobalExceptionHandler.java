package com.example.bookservice.handler;

import jakarta.ws.rs.NotFoundException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientRequestException;

import java.net.ConnectException;
import java.util.List;
import java.util.stream.Collectors;


@RestControllerAdvice
public class GlobalExceptionHandler {

    //Handles other exceptions
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<?> handleIllegalStateException(IllegalStateException exception, WebRequest request){
        String requestUri = ((ServletWebRequest)request).getRequest().getRequestURI().toString();

        ExceptionMessage exceptionMessage = new ExceptionMessage(exception.getMessage(), requestUri);

        return new ResponseEntity<>(exceptionMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST);

    }

    //Handles not found exception
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFound(NotFoundException exception, WebRequest request){
        String requestUri = ((ServletWebRequest)request).getRequest().getRequestURI().toString();

        ExceptionMessage exceptionMessage = new ExceptionMessage(exception.getMessage(), requestUri);

        return new ResponseEntity<>(exceptionMessage, new HttpHeaders(), HttpStatus.NOT_FOUND);

    }

    //Handles the exceptions thrown by jakarta validation
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleValidationException(MethodArgumentNotValidException ex)
    {
        List<String> errorMessages = ((MethodArgumentNotValidException)ex)
                .getBindingResult()
                .getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        return new ResponseEntity(errorMessages.toString(), HttpStatus.BAD_REQUEST);
    }


}


