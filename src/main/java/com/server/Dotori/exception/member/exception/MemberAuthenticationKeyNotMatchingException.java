package com.server.Dotori.exception.member.exception;

public class MemberAuthenticationKeyNotMatchingException extends RuntimeException {

    public MemberAuthenticationKeyNotMatchingException(String msg, Throwable t) {
        super(msg, t);
    }
    public MemberAuthenticationKeyNotMatchingException(String msg) {
        super(msg);
    }
    public MemberAuthenticationKeyNotMatchingException() {
        super();
    }
}
