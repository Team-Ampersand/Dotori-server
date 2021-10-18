package com.server.Dotori.exception.selfstudy.exception;

public class SelfStudyCantRequestTimeException extends RuntimeException {
    public SelfStudyCantRequestTimeException(String msg, Throwable t) {
        super(msg, t);
    }
    public SelfStudyCantRequestTimeException(String msg) {
        super(msg);
    }
    public SelfStudyCantRequestTimeException() {
        super();
    }
}
