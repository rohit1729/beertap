package com.codesherpa.beerdispenser.app.exceptions;

public class ServerException {
    public String error_message;

    public ServerException(String msg) {
        this.error_message = msg;
    }
}
