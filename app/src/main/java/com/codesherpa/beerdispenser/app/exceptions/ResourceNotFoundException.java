package com.codesherpa.beerdispenser.app.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ResourceNotFoundException extends Exception {
    private String message;
}
