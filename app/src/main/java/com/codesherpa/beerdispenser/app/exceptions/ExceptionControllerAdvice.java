package com.codesherpa.beerdispenser.app.exceptions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
class ExceptionControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ServerException> handleValidationExceptions(
    MethodArgumentNotValidException ex) {
        List<ServerException> errors = new ArrayList();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            errors.add(new ServerException(error.getDefaultMessage()));
        });
        return errors;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public List<ServerException> handleConstraintViolationException(ConstraintViolationException ex) {
        List<ServerException> errors = new ArrayList();
        ex.getConstraintViolations().forEach((violation) -> {
            errors.add(new ServerException(violation.getMessage()));
        });;
        return errors;
    }  
}
