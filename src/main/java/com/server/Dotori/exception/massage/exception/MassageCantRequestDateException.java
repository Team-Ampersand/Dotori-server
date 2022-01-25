package com.server.Dotori.exception.massage.exception;

public class MassageCantRequestDateException extends RuntimeException {
    public MassageCantRequestDateException(String msg, Throwable t) {
        super(msg, t);
    }
    public MassageCantRequestDateException(String msg) {
        super(msg);
    }
    public MassageCantRequestDateException() {
        super();
    }
}
