package com.server.Dotori.exception.rule.exception;

public class RuleNoHistoryException extends RuntimeException {

    public RuleNoHistoryException(String msg, Throwable t) {
        super(msg, t);
    }
    public RuleNoHistoryException(String msg) {
        super(msg);
    }
    public RuleNoHistoryException() {
        super();
    }

}
