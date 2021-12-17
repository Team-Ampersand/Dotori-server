package com.server.Dotori.exception.user.exception;

public class UserNoInformationException extends RuntimeException {
    public UserNoInformationException(String msg, Throwable t) {
        super(msg, t);
    }
    public UserNoInformationException(String msg) {
        super(msg);
    }
    public UserNoInformationException() {
        super();
    }
}
