package com.server.Dotori.exception.selfstudy.exception;

public class SelfStudyCantChangeException extends RuntimeException {
    public SelfStudyCantChangeException(String msg, Throwable t) {
        super(msg, t);
    }
    public SelfStudyCantChangeException(String msg) {
        super(msg);
    }
    public SelfStudyCantChangeException() {
        super();
    }
}
