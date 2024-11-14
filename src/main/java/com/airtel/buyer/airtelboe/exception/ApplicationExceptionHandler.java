package com.airtel.buyer.airtelboe.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(BoeException.class)
    public ResponseEntity<?> handleException(BoeException e) {
        return new ResponseEntity<>(e.boeResponse, e.httpStatus);
    }

}
