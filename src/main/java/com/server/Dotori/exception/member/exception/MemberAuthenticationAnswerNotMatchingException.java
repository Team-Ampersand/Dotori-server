package com.server.Dotori.exception.member.exception;

public class UserAuthenticationAnswerNotMatchingException extends RuntimeException {
    public UserAuthenticationAnswerNotMatchingException(String msg, Throwable t) {
        super(msg, t);
    }
    public UserAuthenticationAnswerNotMatchingException(String msg) {
        super(msg);
    }
    public UserAuthenticationAnswerNotMatchingException() {
        super();
    }
}
