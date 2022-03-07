package com.server.Dotori.new_exception.handler;

import com.server.Dotori.new_exception.ErrorCode;
import com.server.Dotori.new_exception.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class UnknownExceptionHandler {

    // 알수없는 에러
    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<ErrorResponse> defaultException(Exception ex){
        log.error("UnknownExceptionHandler Throw Exception : {}", ex.getMessage(), ex);
        return ErrorResponse.toResponseEntity(ErrorCode.UNKNOWN_ERROR);
    }
}
