package com.codesherpa.beerdispenser.app.exceptions;

import java.util.LinkedList;
import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InputValidationException extends Exception {
    public List<ServerException> exceptions = new LinkedList();
    public HttpStatus expectedResponseCode;
}
