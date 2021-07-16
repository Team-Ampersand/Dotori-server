package com.server.Dotori.exception.board.exception;

public class BoardNotFoundException extends RuntimeException {

    public BoardNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }
    public BoardNotFoundException(String msg) {
        super(msg);
    }
    public BoardNotFoundException() {
        super();
    }
}
