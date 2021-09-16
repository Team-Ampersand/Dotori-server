package com.server.Dotori.exception.selfstudy.exception;

public class SelfStudyOverPersonal extends RuntimeException {
    public SelfStudyOverPersonal(String msg, Throwable t) {
        super(msg, t);
    }
    public SelfStudyOverPersonal(String msg){
        super(msg);
    }
    public SelfStudyOverPersonal() {
        super();
    }
}
