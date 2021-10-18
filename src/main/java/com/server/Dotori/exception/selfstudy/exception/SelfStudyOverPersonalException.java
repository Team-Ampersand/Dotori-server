package com.server.Dotori.exception.selfstudy.exception;

public class SelfStudyOverPersonalException extends RuntimeException {
    public SelfStudyOverPersonalException(String msg, Throwable t) {
        super(msg, t);
    }
    public SelfStudyOverPersonalException(String msg){
        super(msg);
    }
    public SelfStudyOverPersonalException() {
        super();
    }
}
