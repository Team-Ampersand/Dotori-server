package com.server.Dotori.exception.board.exception;

public class BoardNotSearchException extends RuntimeException {

    public BoardNotSearchException(String msg, Throwable t) {
        super(msg, t);
    }
    public BoardNotSearchException(String msg) {
        super(msg);
    }
    public BoardNotSearchException() {
        super();
    }
}
