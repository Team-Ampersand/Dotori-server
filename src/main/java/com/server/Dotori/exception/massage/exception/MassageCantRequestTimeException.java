package com.server.Dotori.exception.massage.exception;

public class MassageCantRequestTime extends RuntimeException {
    public MassageCantRequestTime(String msg, Throwable t) {
        super(msg, t);
    }
    public MassageCantRequestTime(String msg) {
        super(msg);
    }
    public MassageCantRequestTime() {
        super();
    }
}
