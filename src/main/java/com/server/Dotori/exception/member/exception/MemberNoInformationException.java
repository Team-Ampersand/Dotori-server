package com.server.Dotori.exception.member.exception;

public class MemberNoInformationException extends RuntimeException {
    public UserNoInformationException(String msg, Throwable t) {
        super(msg, t);
    }
    public UserNoInformationException(String msg) {
        super(msg);
    }
    public UserNoInformationException() {
        super();
    }
}
