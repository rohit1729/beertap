package com.codesherpa.beerdispenser.app.exceptions;

import java.util.LinkedList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
class ExceptionControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ServerException> handleValidationExceptions(
    MethodArgumentNotValidException ex) {
        List<ServerException> errors = new LinkedList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            errors.add(new ServerException(error.getDefaultMessage()));
        });
        return errors;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public List<ServerException> handleConstraintViolationException(ConstraintViolationException ex) {
        List<ServerException> errors = new LinkedList<>();
        ex.getConstraintViolations().forEach((violation) -> {
            errors.add(new ServerException(violation.getMessage()));
        });;
        return errors;
    }

    @ExceptionHandler(InputValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public List<ServerException> handleInputValidationException(InputValidationException ex) {    
        return ex.exceptions;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public List<ServerException> handleGenericException(InputValidationException ex) {  
        List<ServerException> exceptions = new LinkedList<>();
        exceptions.add(new ServerException("Something went wrong"));
        return ex.exceptions;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public List<ServerException> handleResourceNotFoundException(ResourceNotFoundException ex) {  
        List<ServerException> exceptions = new LinkedList<>();
        exceptions.add(new ServerException(ex.getMessage()));
        return exceptions;
    }

    @ExceptionHandler(UnprocessableEntityException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    public List<ServerException> handleUnprocessableEntityException(UnprocessableEntityException ex) {  
        List<ServerException> exceptions = new LinkedList<>();
        exceptions.add(new ServerException(ex.getMessage()));
        return exceptions;
    }
}
