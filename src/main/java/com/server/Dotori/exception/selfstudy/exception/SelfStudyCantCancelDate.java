package com.server.Dotori.exception.selfstudy.exception;

public class SelfStudyCantCancelDate extends RuntimeException {
    public SelfStudyCantCancelDate(String msg, Throwable t) {
        super(msg, t);
    }
    public SelfStudyCantCancelDate(String msg) {
        super(msg);
    }
    public SelfStudyCantCancelDate() {
        super();
    }
}
