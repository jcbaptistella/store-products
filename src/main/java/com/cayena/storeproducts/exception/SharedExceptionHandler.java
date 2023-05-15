package com.cayena.storeproducts.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class SharedExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({GenericException.class})
    @ResponseStatus(BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleGenericExceptions(GenericException ex, WebRequest request) {
        ErrorResponse error = new ErrorResponse(BAD_REQUEST, List.of(ex.getMessage()));
        return new ResponseEntity<>(error, BAD_REQUEST);
    }

    @ExceptionHandler({NotFoundException.class})
    @ResponseStatus(NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleNotFoundExceptions(NotFoundException ex, WebRequest request) {
        ErrorResponse error = new ErrorResponse(NOT_FOUND, List.of(ex.getMessage()));
        return new ResponseEntity<>(error, NOT_FOUND);
    }
}
