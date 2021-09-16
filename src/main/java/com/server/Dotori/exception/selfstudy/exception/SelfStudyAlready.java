package com.server.Dotori.exception.selfstudy.exception;

public class SelfStudyAlready extends RuntimeException {
    public SelfStudyAlready(String msg, Throwable t) {
        super(msg, t);
    }
    public SelfStudyAlready(String msg) {
        super(msg);
    }
    public SelfStudyAlready() {
        super();
    }
}
