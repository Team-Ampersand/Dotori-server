package com.server.Dotori.exception.member.exception;

public class MemberAlreadyJoinThisNameException extends RuntimeException {
    public MemberAlreadyJoinThisNameException(String msg, Throwable t) {
        super(msg, t);
    }

    public MemberAlreadyJoinThisNameException(String msg) {
        super(msg);
    }

    public MemberAlreadyJoinThisNameException() {
        super();
    }
}