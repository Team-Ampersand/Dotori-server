package com.server.Dotori.exception.user.exception;

public class UserNotFoundByClassException extends RuntimeException {
    public UserNotFoundByClassException(String msg, Throwable t) {
        super(msg, t);
    }
    public UserNotFoundByClassException(String msg) {
        super(msg);
    }
    public UserNotFoundByClassException() {
        super();
    }
}
