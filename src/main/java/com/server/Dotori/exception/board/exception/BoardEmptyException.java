package com.server.Dotori.exception.board.exception;

public class BoardEmptyException extends RuntimeException {
    public BoardEmptyException(String msg, Throwable t){
        super(msg, t);
    }
    public BoardEmptyException(String msg) {
        super(msg);
    }
    public BoardEmptyException() {
        super();
    }
}
