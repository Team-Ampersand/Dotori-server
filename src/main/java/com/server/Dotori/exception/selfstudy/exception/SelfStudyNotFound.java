package com.server.Dotori.exception.selfstudy.exception;

public class SelfStudyNotFound extends RuntimeException {
    public SelfStudyNotFound(String msg, Throwable t) {
        super(msg, t);
    }
    public SelfStudyNotFound(String msg) {
        super(msg);
    }
    public SelfStudyNotFound() {
        super();
    }
}
