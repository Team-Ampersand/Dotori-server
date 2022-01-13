package com.server.Dotori.exception.member.exception;

public class MemberAlreadyJoinThisStunumException extends RuntimeException {
    public MemberAlreadyJoinThisStunumException(String msg, Throwable t) {
        super(msg, t);
    }
    public MemberAlreadyJoinThisStunumException(String msg) {
        super(msg);
    }
    public MemberAlreadyJoinThisStunumException() {
        super();
    }
}
