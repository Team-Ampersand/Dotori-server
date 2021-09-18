package com.server.Dotori.exception.user.exception;

public class UserAlreadyJoinThisNameException extends RuntimeException {
    public UserAlreadyJoinThisNameException(String msg, Throwable t) {
        super(msg, t);
    }

    public UserAlreadyJoinThisNameException(String msg) {
        super(msg);
    }

    public UserAlreadyJoinThisNameException() {
        super();
    }
}
