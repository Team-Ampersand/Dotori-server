package com.server.Dotori.exception.token;

import com.server.Dotori.exception.token.exception.AccessTokenExpiredException;
import com.server.Dotori.exception.token.exception.InvalidTokenException;
import com.server.Dotori.exception.token.exception.LogoutTokenException;
import com.server.Dotori.exception.token.exception.RefreshTokenFailException;
import com.server.Dotori.response.result.CommonResult;
import com.server.Dotori.util.ExceptionResponseObjectUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor @Order(Ordered.HIGHEST_PRECEDENCE)
@Service
public class TokenExceptionHandlerImpl implements TokenExceptionHandler {

    private ExceptionResponseObjectUtil exceptionResponseObjectUtil;

    @Override
    public CommonResult accessTokenExpiredException(AccessTokenExpiredException ex) {
        log.debug("=== Access Token Expired Exception 발생 ===");
        return exceptionResponseObjectUtil.getExceptionResponseObject(ACCESS_TOKEN_EXPIRED);
    }

    @Override
    public CommonResult invalidTokenException(InvalidTokenException ex) {
        log.debug("=== Invalid Token Exception 발생 ===");
        return exceptionResponseObjectUtil.getExceptionResponseObject(INVALID_TOKEN);
    }

    @Override
    public CommonResult logoutTokenException(LogoutTokenException ex) {
        log.debug("=== Logout Token Exception 발생 ===");
        return exceptionResponseObjectUtil.getExceptionResponseObject(LOGOUT_TOKEN);
    }

    @Override
    public CommonResult refreshTokenFailException(RefreshTokenFailException ex) {
        log.debug("=== Refresh Token Fail Exception 발생 ===");
        return exceptionResponseObjectUtil.getExceptionResponseObject(REFRESH_TOKEN_FAIL);
    }
}