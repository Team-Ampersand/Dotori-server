package com.server.Dotori.exception.selfstudy.exception;

public class SelfStudyNotFoundStudent extends RuntimeException {
    public SelfStudyNotFoundStudent(String msg, Throwable t) {
        super(msg, t);
    }
    public SelfStudyNotFoundStudent(String msg) {
        super(msg);
    }
    public SelfStudyNotFoundStudent() {
        super();
    }
}
