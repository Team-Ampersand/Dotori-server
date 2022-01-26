package com.server.Dotori.exception.massage.exception;

public class MassageCantRequestTimeException extends RuntimeException {
    public MassageCantRequestTimeException(String msg, Throwable t) {
        super(msg, t);
    }
    public MassageCantRequestTimeException(String msg) {
        super(msg);
    }
    public MassageCantRequestTimeException() {
        super();
    }
}
