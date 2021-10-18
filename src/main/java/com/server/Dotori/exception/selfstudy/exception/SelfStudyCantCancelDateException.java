package com.server.Dotori.exception.selfstudy.exception;

public class SelfStudyCantCancelDateException extends RuntimeException {
    public SelfStudyCantCancelDateException(String msg, Throwable t) {
        super(msg, t);
    }
    public SelfStudyCantCancelDateException(String msg) {
        super(msg);
    }
    public SelfStudyCantCancelDateException() {
        super();
    }
}
