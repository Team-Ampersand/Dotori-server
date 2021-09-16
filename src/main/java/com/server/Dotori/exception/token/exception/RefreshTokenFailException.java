package com.server.Dotori.exception.token.exception;

public class RefreshTokenFailException extends RuntimeException {

    public RefreshTokenFailException(String msg, Throwable t) {
        super(msg, t);
    }
    public RefreshTokenFailException(String msg) {
        super(msg);
    }
    public RefreshTokenFailException() {
        super();
    }

}
