package com.server.Dotori.exception.selfstudy.exception;

public class SelfStudyCantRequestDateException extends RuntimeException {
    public SelfStudyCantRequestDateException(String msg, Throwable t) {
        super(msg, t);
    }
    public SelfStudyCantRequestDateException(String msg) {
        super(msg);
    }
    public SelfStudyCantRequestDateException() {
        super();
    }
}
