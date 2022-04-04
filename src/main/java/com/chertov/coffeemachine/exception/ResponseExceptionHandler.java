package com.chertov.coffeemachine.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(OutOfCoffeeException.class)
    public ResponseEntity<Object> handle(OutOfCoffeeException e, WebRequest request) {
        String message = e.getMessage();
        return handleExceptionInternal(e, message, new HttpHeaders(), HttpStatus.BAD_REQUEST,
                                       request);
    }
}
