package com.server.Dotori.exception.member.exception;

public class MemberNotFoundByClassException extends RuntimeException {
    public MemberNotFoundByClassException(String msg, Throwable t) {
        super(msg, t);
    }
    public MemberNotFoundByClassException(String msg) {
        super(msg);
    }
    public MemberNotFoundByClassException() {
        super();
    }
}
