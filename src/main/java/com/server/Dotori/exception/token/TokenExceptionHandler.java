package com.server.Dotori.exception.token;

import com.server.Dotori.exception.token.exception.*;
import com.server.Dotori.response.result.CommonResult;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface TokenExceptionHandler {

    String ACCESS_TOKEN_EXPIRED = "access-token-expired";
    String INVALID_TOKEN = "invalid-token";
    String LOGOUT_TOKEN = "logout-token";
    String REFRESH_TOKEN_FAIL = "refresh-token-fail";
    String TOKEN_IS_EMPTY = "token-is-empty";

    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    CommonResult expiredJwtException(ExpiredJwtException ex);

    @ExceptionHandler(InvalidTokenException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    CommonResult invalidTokenException(InvalidTokenException ex);

    @ExceptionHandler(LogoutTokenException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    CommonResult logoutTokenException(LogoutTokenException ex);

    @ExceptionHandler(RefreshTokenFailException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    CommonResult refreshTokenFailException(RefreshTokenFailException ex);

    @ExceptionHandler(TokenIsEmptyException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    CommonResult tokenIsEmptyException(TokenIsEmptyException ex);

}
