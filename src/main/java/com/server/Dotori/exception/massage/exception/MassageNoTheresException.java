package com.server.Dotori.exception.massage.exception;

public class MassageNoTheresException extends RuntimeException{
    public MassageNoTheresException(String msg, Throwable t) {
        super(msg, t);
    }
    public MassageNoTheresException(String msg) {
        super(msg);
    }
    public MassageNoTheresException() {
        super();
    }
}
