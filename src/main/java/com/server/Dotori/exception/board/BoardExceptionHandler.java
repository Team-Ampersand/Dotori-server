package com.server.Dotori.exception.board;

import com.server.Dotori.exception.board.exception.*;
import com.server.Dotori.response.result.CommonResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface BoardExceptionHandler {

    String BOARD_NOT_FOUND = "board-not-found";
    String BOARD_NOT_HAVE_PERMISSION_TO_CREATE = "board-not-have-permission-to-create";
    String BOARD_NOT_HAVE_PERMISSION_TO_MODIFY = "board-not-have-permission-to-modify";
    String BOARD_NOT_HAVE_PERMISSION_TO_DELETE = "board-not-have-permission-to-delete";
    String BOARD_EMPTY = "board-empty";

    @ExceptionHandler(BoardNotHavePermissionToCreate.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    CommonResult boardNotHavePermissionToCreate(BoardNotHavePermissionToCreate ex);

    @ExceptionHandler(BoardNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    CommonResult boardNotFoundException(BoardNotFoundException ex);

    @ExceptionHandler(BoardNotHavePermissionToModify.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    CommonResult boardNotHavePermissionToModify(BoardNotHavePermissionToModify ex);

    @ExceptionHandler(BoardNotHavePermissionToDelete.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    CommonResult boardNotHavePermissionToDelete(BoardNotHavePermissionToDelete ex);

    @ExceptionHandler(BoardEmptyException.class)
    @ResponseStatus(HttpStatus.OK)
    CommonResult boardEmptyException(BoardEmptyException ex);
}
