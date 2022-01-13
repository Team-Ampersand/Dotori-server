package com.server.Dotori.exception.member.exception;

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
