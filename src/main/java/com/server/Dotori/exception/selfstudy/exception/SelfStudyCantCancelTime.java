package com.server.Dotori.exception.selfstudy.exception;

public class SelfStudyCantCancelTime extends RuntimeException {
    public SelfStudyCantCancelTime(String msg, Throwable t) {
        super(msg, t);
    }
    public SelfStudyCantCancelTime(String msg) {
        super(msg);
    }
    public SelfStudyCantCancelTime() {
        super();
    }
}
