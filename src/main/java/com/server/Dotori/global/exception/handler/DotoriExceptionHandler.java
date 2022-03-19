package com.server.Dotori.global.exception.handler;

import com.server.Dotori.global.exception.DotoriException;
import com.server.Dotori.global.exception.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class DotoriExceptionHandler {

    @ExceptionHandler(value = {DotoriException.class})
    public ResponseEntity<ErrorResponse> handleDotoriException(DotoriException e) {
        log.error("handleDotoriException throw DotoriException : {}", e.getErrorCode());
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> handleInvalidRequestDataException(MethodArgumentNotValidException e){
        log.error("handleInvalidRequestDataException throw MethodArgumentNotValidException");
        BindingResult result = e.getBindingResult();
        StringBuilder builder = new StringBuilder();

        for (FieldError fieldError : result.getFieldErrors()) {
            builder.append(fieldError.getField()).append(":");
            builder.append(fieldError.getDefaultMessage());
            builder.append(",");
        }

        builder.deleteCharAt(builder.lastIndexOf(","));
        return ErrorResponse.toResponseEntity(HttpStatus.BAD_REQUEST, builder.toString());

    }

}
