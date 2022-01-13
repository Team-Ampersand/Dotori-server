package com.server.Dotori.exception.member.exception;

public class MemberAlreadyJoinThisStunumException extends RuntimeException {
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
