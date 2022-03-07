package com.server.Dotori.new_exception.handler;

import com.server.Dotori.new_exception.ErrorResponse;
import com.server.Dotori.new_exception.exception.massage.MassageAlreadyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class DotoriExceptionHandler {

//    @ExceptionHandler(MassageAlreadyException.class)
//    public ResponseEntity<ErrorResponse> handleMassageAlreadyException(MassageAlreadyException ex) {
//        log.error("Handle Massage Already Exception", ex);
//        ErrorResponse response = new ErrorResponse(ex.getErrorCode());
//        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
//    }
}
