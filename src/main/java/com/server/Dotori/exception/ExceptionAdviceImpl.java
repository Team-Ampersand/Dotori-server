package com.server.Dotori.exception;

import com.server.Dotori.exception.ExceptionAdvice;
import com.server.Dotori.exception.customError.exception.CustomForbiddenException;
import com.server.Dotori.exception.customError.exception.CustomNotFoundException;
import com.server.Dotori.exception.customError.exception.CustomUnauthorizedException;
import com.server.Dotori.exception.token.exception.AccessTokenExpiredException;
import com.server.Dotori.exception.token.exception.InvalidTokenException;
import com.server.Dotori.exception.user.exception.UserNotFoundException;
import com.server.Dotori.response.ResponseService;
import com.server.Dotori.response.result.CommonResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionAdviceImpl implements ExceptionAdvice {

    private final ResponseService responseService;
    private final MessageSource messageSource;

    // code 정보에 해당하는 메시지를 조회한다.
    private String getMessage(String code){
        return getMessage(code, null);
    }

    // code 정보, 추가 argument로 현재 locale에 맞는 메시지를 조회합니다.
    private String getMessage(String code, Object[] args){
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }


     // *** Exception 처리시 i18n애 정의 되어있는 exception code, msg를 가져온다. ***
    private CommonResult getExceptionResponseObj(String code){
        return responseService.getFailResult(Integer.valueOf(getMessage(code + ".code")), getMessage(code + ".msg"));
    }

    // 알수없는 에러
    @Override
    public CommonResult defaultException(Exception ex){
        log.error("=== 알 수 없는 에러 발생 ===", ex);
        CommonResult exceptionResponseObj = getExceptionResponseObj(DEFAULT_EXCEPTION);
        return exceptionResponseObj;
    }


    /*** Custom Server Exception ***/
    @Override
    public CommonResult unauthorized(CustomUnauthorizedException ex) {
        log.debug("=== Unauthorized Exception 발생 ===");
        return getExceptionResponseObj(CUSTOM_401_UNAUTHORIZED);
    }

    @Override
    public CommonResult forbiddenException(CustomForbiddenException ex) {
        log.debug("=== Forbidden Exception 발생 ===");
        return getExceptionResponseObj(CUSTOM_403_FORBIDDEN);
    }

    @Override
    public CommonResult notFoundException(CustomNotFoundException ex) {
        log.debug("=== NotFound Exception 발생 ===");
        return getExceptionResponseObj(CUSTOM_404_NOT_FOUND);
    }


    /*** UserException ***/
    @Override
    public CommonResult userNotFoundException(UserNotFoundException ex){
        log.debug("=== User Not Found Exception 발생 ===");
        return getExceptionResponseObj(USER_NOT_FOUND);
    }

    /*** Token Exceptions ***/
    @Override
    public CommonResult accessTokenExpiredException(AccessTokenExpiredException ex) {
        log.debug("=== Access Token Expired Exception 발생 ===");
        return getExceptionResponseObj(ACCESS_TOKEN_EXPIRED);
    }

    @Override
    public CommonResult invalidTokenException(InvalidTokenException ex) {
        log.debug("=== Invalid Token Exception 발생 ===");
        return getExceptionResponseObj(INVALID_TOKEN);
    }
}
