package com.server.Dotori.exception.unknown_exception;

import com.server.Dotori.new_exception.ErrorCode;
import com.server.Dotori.new_exception.ErrorResponse;
import com.server.Dotori.response.result.CommonResult;
import com.server.Dotori.util.ExceptionResponseObjectUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice @Order(Ordered.LOWEST_PRECEDENCE)
@RequiredArgsConstructor
public class UnknownExceptionHandler {

    public static String DEFAULT_EXCEPTION = "unknown";

    private final ExceptionResponseObjectUtil exceptionResponseObjectUtil;

    // 알수없는 에러
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> defaultException(Exception ex){
        log.error("Unknown Error", ex);
        ErrorResponse response = new ErrorResponse(ErrorCode.UNKNOWN_ERROR);
        return new ResponseEntity<>(response, HttpStatus.valueOf(().getStatus()));
    }
}
