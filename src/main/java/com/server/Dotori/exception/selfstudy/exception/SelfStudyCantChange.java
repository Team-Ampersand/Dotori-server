package com.server.Dotori.exception.selfstudy.exception;

public class SelfStudyCantChange extends RuntimeException {
    public SelfStudyCantChange(String msg, Throwable t) {
        super(msg, t);
    }
    public SelfStudyCantChange(String msg) {
        super(msg);
    }
    public SelfStudyCantChange() {
        super();
    }
}
