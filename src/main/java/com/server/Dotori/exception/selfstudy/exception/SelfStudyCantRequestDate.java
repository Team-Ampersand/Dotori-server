package com.server.Dotori.exception.selfstudy.exception;

public class SelfStudyCantRequestDate extends RuntimeException {
    public SelfStudyCantRequestDate(String msg, Throwable t) {
        super(msg, t);
    }
    public SelfStudyCantRequestDate(String msg) {
        super(msg);
    }
    public SelfStudyCantRequestDate() {
        super();
    }
}
