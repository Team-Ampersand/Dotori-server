package com.server.Dotori.exception.selfstudy.exception;

public class SelfStudyCantCancelTimeException extends RuntimeException {
    public SelfStudyCantCancelTimeException(String msg, Throwable t) {
        super(msg, t);
    }
    public SelfStudyCantCancelTimeException(String msg) {
        super(msg);
    }
    public SelfStudyCantCancelTimeException() {
        super();
    }
}
