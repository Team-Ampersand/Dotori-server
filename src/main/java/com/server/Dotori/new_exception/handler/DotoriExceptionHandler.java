package com.server.Dotori.new_exception.handler;

import com.server.Dotori.new_exception.DotoriException;
import com.server.Dotori.new_exception.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {DotoriException.class})
    public ResponseEntity<ErrorResponse> handleDotoriException(DotoriException e) {
        log.error("handleCustomException throw DotoriException : {}", e.getErrorCode());
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }
}
