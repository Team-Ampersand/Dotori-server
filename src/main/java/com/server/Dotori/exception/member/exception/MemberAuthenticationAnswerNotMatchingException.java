package com.server.Dotori.exception.member.exception;

public class MemberAuthenticationAnswerNotMatchingException extends RuntimeException {
    public MemberAuthenticationAnswerNotMatchingException(String msg, Throwable t) {
        super(msg, t);
    }
    public MemberAuthenticationAnswerNotMatchingException(String msg) {
        super(msg);
    }
    public MemberAuthenticationAnswerNotMatchingException() {
        super();
    }
}
