package com.server.Dotori.exception.board.exception;

public class BoardIsEmptyException extends RuntimeException {
    public BoardIsEmptyException(String msg, Throwable t){
        super(msg, t);
    }
    public BoardIsEmptyException(String msg) {
        super(msg);
    }
    public BoardIsEmptyException() {
        super();
    }
}
