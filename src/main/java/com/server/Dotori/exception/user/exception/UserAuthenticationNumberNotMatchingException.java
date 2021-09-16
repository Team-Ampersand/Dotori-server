package com.server.Dotori.exception.user.exception;

public class UserAuthenticationNumberNotMatchingException extends RuntimeException {
    public UserAuthenticationNumberNotMatchingException(String msg, Throwable t) {
        super(msg, t);
    }
    public UserAuthenticationNumberNotMatchingException(String msg) {
        super(msg);
    }
    public UserAuthenticationNumberNotMatchingException() {
        super();
    }
}
