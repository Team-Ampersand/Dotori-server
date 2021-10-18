package com.server.Dotori.exception.token.exception;

public class TokenIsEmptyException extends RuntimeException {
    public TokenIsEmptyException(String msg, Throwable t) {
        super(msg, t);
    }
    public TokenIsEmptyException(String msg) {
        super(msg);
    }
    public TokenIsEmptyException() {
        super();
    }
}
