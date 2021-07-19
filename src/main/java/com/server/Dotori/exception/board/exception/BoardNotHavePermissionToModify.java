package com.server.Dotori.exception.board.exception;

public class BoardNotHavePermissionToModify extends RuntimeException {

    public BoardNotHavePermissionToModify(String msg, Throwable t) {
        super(msg, t);
    }
    public BoardNotHavePermissionToModify(String msg) {
        super(msg);
    }
    public BoardNotHavePermissionToModify() {
        super();
    }
}
