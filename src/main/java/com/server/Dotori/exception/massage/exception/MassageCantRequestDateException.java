package com.server.Dotori.exception.massage.exception;

public class MassageCantRequestDateException extends RuntimeException {
    public MassageCantRequestDate(String msg, Throwable t) {
        super(msg, t);
    }
    public MassageCantRequestDate(String msg) {
        super(msg);
    }
    public MassageCantRequestDate() {
        super();
    }
}
