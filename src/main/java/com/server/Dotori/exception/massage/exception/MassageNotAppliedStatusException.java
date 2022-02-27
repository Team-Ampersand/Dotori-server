package com.server.Dotori.exception.massage.exception;

public class MassageNotAppliedStatusException extends RuntimeException {
    public MassageNotAppliedStatusException(String msg, Throwable t) {
        super(msg, t);
    }
    public MassageNotAppliedStatusException(String msg) {
        super(msg);
    }
    public MassageNotAppliedStatusException() {
        super();
    }
}
