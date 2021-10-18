package com.server.Dotori.exception.selfstudy.exception;

public class SelfStudyCantAppliedException extends RuntimeException {
    public SelfStudyCantAppliedException(String msg, Throwable t) {
        super(msg, t);
    }
    public SelfStudyCantAppliedException(String msg) {
        super(msg);
    }
    public SelfStudyCantAppliedException() {
        super();
    }
}
