package com.server.Dotori.exception.token;

import com.server.Dotori.exception.token.exception.AccessTokenExpiredException;
import com.server.Dotori.exception.token.exception.InvalidTokenException;
import com.server.Dotori.exception.token.exception.LogoutTokenException;
import com.server.Dotori.exception.token.exception.RefreshTokenFailException;
import com.server.Dotori.response.result.CommonResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface TokenExceptionHandler {

    String ACCESS_TOKEN_EXPIRED = "access-token-expired";
    String INVALID_TOKEN = "invalid-token";
    String LOGOUT_TOKEN = "logout-token";
    String REFRESH_TOKEN_FAIL = "refresh-token-fail";

    /*** Token Exceptions ***/
    // 액세스 토큰이 만료되었습니다.
    @ExceptionHandler(AccessTokenExpiredException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    CommonResult accessTokenExpiredException(AccessTokenExpiredException ex);

    // 올바르지 않은 토큰
    @ExceptionHandler(InvalidTokenException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    CommonResult invalidTokenException(InvalidTokenException ex);

    @ExceptionHandler(LogoutTokenException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    CommonResult logoutTokenException(LogoutTokenException ex);

    @ExceptionHandler(RefreshTokenFailException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    CommonResult refreshTokenFailException(RefreshTokenFailException ex);

}