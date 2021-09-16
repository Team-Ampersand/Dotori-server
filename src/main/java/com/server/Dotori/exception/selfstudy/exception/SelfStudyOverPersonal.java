package com.server.Dotori.exception.selfstudy.exception;

public class SelfStudyOverPersonalMaximum extends RuntimeException {
    public SelfStudyOverPersonalMaximum(String msg, Throwable t) {
        super(msg, t);
    }
    public SelfStudyOverPersonalMaximum(String msg){
        super(msg);
    }
    public SelfStudyOverPersonalMaximum() {
        super();
    }
}
