package com.server.Dotori.exception.unknown_exception;

import com.server.Dotori.response.ResponseService;
import com.server.Dotori.response.result.CommonResult;
import com.server.Dotori.util.ExceptionResponseObjectUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice @Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
public class UnknownExceptionHandler {

    public String DEFAULT_EXCEPTION = "unknown";

    private ExceptionResponseObjectUtil exceptionResponseObjectUtil;

    // 알수없는 에러
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult defaultException(Exception ex){
        log.error("=== 알 수 없는 에러 발생 ===", ex);
        return exceptionResponseObjectUtil.getExceptionResponseObject(DEFAULT_EXCEPTION);
    }
}
