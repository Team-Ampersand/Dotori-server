package com.server.Dotori.exception.member.exception;

public class MemberNoInformationException extends RuntimeException {
    public MemberNoInformationException(String msg, Throwable t) {
        super(msg, t);
    }
    public MemberNoInformationException(String msg) {
        super(msg);
    }
    public MemberNoInformationException() {
        super();
    }
}
