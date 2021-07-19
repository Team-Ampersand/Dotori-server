package com.server.Dotori.exception;

import com.server.Dotori.exception.board.exception.BoardNotFoundException;
import com.server.Dotori.exception.board.exception.BoardNotHavePermissionToCreate;
import com.server.Dotori.exception.board.exception.BoardNotHavePermissionToDelete;
import com.server.Dotori.exception.board.exception.BoardNotHavePermissionToModify;
import com.server.Dotori.exception.customError.exception.CustomForbiddenException;
import com.server.Dotori.exception.customError.exception.CustomNotFoundException;
import com.server.Dotori.exception.customError.exception.CustomUnauthorizedException;
import com.server.Dotori.exception.token.exception.AccessTokenExpiredException;
import com.server.Dotori.exception.token.exception.InvalidTokenException;
import com.server.Dotori.exception.user.exception.UserAlreadyException;
import com.server.Dotori.exception.user.exception.UserNotFoundException;
import com.server.Dotori.exception.user.exception.UserPasswordNotMatchingException;
import com.server.Dotori.response.result.CommonResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface ExceptionAdvice {

    String DEFAULT_EXCEPTION = "unknown";

    String CUSTOM_401_UNAUTHORIZED = "unauthorized";
    String CUSTOM_403_FORBIDDEN = "forbidden";
    String CUSTOM_404_NOT_FOUND = "not-found";

    String USER_NOT_FOUND = "user-not-found";
    String USER_ALREADY = "user-already";
    String USER_PASSWORD_NOT_MATCHING = "user-password-not-matching";

    String ACCESS_TOKEN_EXPIRED = "access-token-expired";
    String INVALID_TOKEN = "invalid-token";

    String BOARD_NOT_FOUND = "board-not-found";
    String BOARD_NOT_HAVE_PERMISSION_TO_CREATE = "board-not-have-permission-to-create";
    String BOARD_NOT_HAVE_PERMISSION_TO_MODIFY = "board-not-have-permission-to-modify";
    String BOARD_NOT_HAVE_PERMISSION_TO_DELETE = "board-not-have-permission-to-delete";

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    CommonResult defaultException(Exception ex);

    /*** Custom Server Exception ***/
    @ExceptionHandler(CustomUnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    CommonResult unauthorized(CustomUnauthorizedException ex);

    @ExceptionHandler(CustomForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    CommonResult forbiddenException(CustomForbiddenException ex);

    @ExceptionHandler(CustomNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    CommonResult notFoundException(CustomNotFoundException ex);


    /*** User Exceptions ***/
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    CommonResult userNotFoundException(UserNotFoundException ex);

    @ExceptionHandler(UserAlreadyException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    CommonResult userAlreadyException(UserAlreadyException ex);


    @ExceptionHandler(UserPasswordNotMatchingException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    CommonResult userPasswordNotMatchingException(UserPasswordNotMatchingException ex);

    /*** Token Exceptions ***/
    // 액세스 토큰이 만료되었습니다.
    @ExceptionHandler(AccessTokenExpiredException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    CommonResult accessTokenExpiredException(AccessTokenExpiredException ex);

    // 올바르지 않은 토큰
    @ExceptionHandler(InvalidTokenException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    CommonResult invalidTokenException(InvalidTokenException ex);

    /*** Board Exception ***/
    //게시물 생성 권한이 없습니다
    @ExceptionHandler(BoardNotHavePermissionToCreate.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    CommonResult boardNotHavePermissionToCreate(BoardNotHavePermissionToCreate ex);

    // 게시물을 찾을 수 없습니다.
    @ExceptionHandler(BoardNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    CommonResult boardNotFoundException(BoardNotFoundException ex);

    // 게시물 수정 권한이 없습니다.
    @ExceptionHandler(BoardNotHavePermissionToModify.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    CommonResult boardNotHavePermissionToModify(BoardNotHavePermissionToModify ex);

    @ExceptionHandler(BoardNotHavePermissionToDelete.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    CommonResult boardNotHavePermissionToDelete(BoardNotHavePermissionToDelete ex);
}
