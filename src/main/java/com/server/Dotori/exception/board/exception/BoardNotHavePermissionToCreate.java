package com.server.Dotori.exception.board.exception;

public class BoardNotHavePermissionToCreate extends RuntimeException {

    public BoardNotHavePermissionToCreate(String msg, Throwable t) {
        super(msg, t);
    }
    public BoardNotHavePermissionToCreate(String msg) {
        super(msg);
    }
    public BoardNotHavePermissionToCreate() {
        super();
    }
}
