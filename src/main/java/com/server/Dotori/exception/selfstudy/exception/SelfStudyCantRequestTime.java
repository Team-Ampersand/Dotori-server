package com.server.Dotori.exception.selfstudy.exception;

public class SelfStudyCantRequestTime extends RuntimeException {
    public SelfStudyCantRequestTime(String msg, Throwable t) {
        super(msg, t);
    }
    public SelfStudyCantRequestTime(String msg) {
        super(msg);
    }
    public SelfStudyCantRequestTime() {
        super();
    }
}
