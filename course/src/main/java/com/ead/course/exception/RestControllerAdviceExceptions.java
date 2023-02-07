package com.ead.course.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class RestControllerAdviceExceptions {
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<?> errorHandler(MethodArgumentNotValidException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage
                ).collect(Collectors.toList())
        );
    }
}
