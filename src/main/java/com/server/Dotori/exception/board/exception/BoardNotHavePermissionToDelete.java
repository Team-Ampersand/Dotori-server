package com.server.Dotori.exception.board.exception;

public class BoardNotHavePermissionToDelete extends RuntimeException {

    public BoardNotHavePermissionToDelete(String msg, Throwable t) {
        super(msg, t);
    }
    public BoardNotHavePermissionToDelete(String msg) {
        super(msg);
    }
    public BoardNotHavePermissionToDelete() {
        super();
    }
}
