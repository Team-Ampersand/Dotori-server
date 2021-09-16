package com.server.Dotori.exception.selfstudy.exception;

public class SelfStudyCantApplied extends RuntimeException {
    public SelfStudyCantApplied(String msg, Throwable t) {
        super(msg, t);
    }
    public SelfStudyCantApplied(String msg) {
        super(msg);
    }
    public SelfStudyCantApplied() {
        super();
    }
}
