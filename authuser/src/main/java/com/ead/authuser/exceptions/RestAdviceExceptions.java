package com.ead.authuser.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestAdviceExceptions {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, List<String>>> exception(MethodArgumentNotValidException exceptions) {
        List<String> erros = exceptions
                .getBindingResult()
                .getAllErrors()
                .stream()
                .map(r -> r.getDefaultMessage())
                .collect(Collectors.toList());
        Map<String,List<String>> mapErrors = new HashMap<>(){{put("errors", erros);}};
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapErrors);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, List<String>>> exception(ResponseStatusException exception) {
        List errors = Arrays.asList(exception.getReason());
        Map<String,List<String>> mapErrors = new HashMap<>(){{put("errors", errors);}};
        return ResponseEntity.status(exception.getStatus()).body(mapErrors);
    }
}
