package com.server.Dotori.exception.selfstudy.exception;

public class SelfStudyNotFoundException extends RuntimeException {
    public SelfStudyNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }
    public SelfStudyNotFoundException(String msg) {
        super(msg);
    }
    public SelfStudyNotFoundException() {
        super();
    }
}
