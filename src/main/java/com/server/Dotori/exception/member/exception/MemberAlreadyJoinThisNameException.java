package com.server.Dotori.exception.member.exception;

public class MemberAlreadyJoinThisNameException extends RuntimeException {
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
