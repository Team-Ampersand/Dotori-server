package com.server.Dotori.exception.massage.exception;

public class MassageAlreadyException extends RuntimeException {
    public MassageAlreadyException(String msg, Throwable t) {
        super(msg, t);
    }
    public MassageAlreadyException(String msg) {
        super(msg);
    }
    public MassageAlreadyException() {
        super();
    }
}
