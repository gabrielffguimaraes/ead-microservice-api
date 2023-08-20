package com.ead.notificationhex.adapters.inbound.exceptions;

import com.ead.notificationhex.adapters.dtos.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class BaseExceptions {
    @ExceptionHandler
    public ResponseEntity<?> illegalArgumentException(IllegalArgumentException ex) {
        log.info("error illegalArgumentException : {}" , ex.getMessage());
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(ex.getMessage()));
    }
}
