package com.server.Dotori.exception.user.exception;

public class UserAlreadyJoinThisStunumException extends RuntimeException {
    public UserAlreadyJoinThisStunumException(String msg, Throwable t) {
        super(msg, t);
    }
    public UserAlreadyJoinThisStunumException(String msg) {
        super(msg);
    }
    public UserAlreadyJoinThisStunumException() {
        super();
    }
}
