package com.server.Dotori.exception.massage.exception;

public class MassageOverException extends RuntimeException {
    public MassageOverException(String msg, Throwable t) {
        super(msg, t);
    }
    public MassageOverException(String msg) {
        super(msg);
    }
    public MassageOverException() {
        super();
    }
}
