package com.server.Dotori.exception.board;

import com.server.Dotori.exception.board.exception.*;
import com.server.Dotori.response.result.CommonResult;
import com.server.Dotori.util.ExceptionResponseObjectUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
@Service
public class BoardExceptionHandlerImpl implements BoardExceptionHandler {

    private ExceptionResponseObjectUtil exceptionResponseObjectUtil;

    @Override
    public CommonResult boardNotHavePermissionToCreate(BoardNotHavePermissionToCreate ex) {
        log.debug("=== Board Not Have Permission To Create Exception 발생 ===");
        return exceptionResponseObjectUtil.getExceptionResponseObject(BOARD_NOT_HAVE_PERMISSION_TO_CREATE);
    }

    @Override
    public CommonResult boardNotFoundException(BoardNotFoundException ex) {
        log.debug("=== Board Not Found Exception 발생 ===");
        return exceptionResponseObjectUtil.getExceptionResponseObject(BOARD_NOT_FOUND);
    }

    @Override
    public CommonResult boardNotHavePermissionToModify(BoardNotHavePermissionToModify ex) {
        log.debug("=== Board Not Have Permission To Modify Exception 발생 ===");
        return exceptionResponseObjectUtil.getExceptionResponseObject(BOARD_NOT_HAVE_PERMISSION_TO_MODIFY);
    }

    @Override
    public CommonResult boardNotHavePermissionToDelete(BoardNotHavePermissionToDelete ex) {
        log.debug("=== Board Not Have Permission To Delete Exception 발생 ===");
        return exceptionResponseObjectUtil.getExceptionResponseObject(BOARD_NOT_HAVE_PERMISSION_TO_DELETE);
    }

    @Override
    public CommonResult boardEmptyException(BoardEmptyException ex) {
        log.debug("=== Board Empty Exception 발생 ===");
        return exceptionResponseObjectUtil.getExceptionResponseObject(BOARD_EMPTY);
    }
}
