package com.server.Dotori.exception.user.exception;

public class UserAuthenticationKeyNotMatchingException extends RuntimeException {
    public UserAuthenticationKeyNotMatchingException(String msg, Throwable t) {
        super(msg, t);
    }
    public UserAuthenticationKeyNotMatchingException(String msg) {
        super(msg);
    }
    public UserAuthenticationKeyNotMatchingException() {
        super();
    }
}
