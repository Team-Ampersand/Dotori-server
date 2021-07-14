package com.server.Dotori.exception.response;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    private final String message;
    private final HttpStatus httpStatus;

    public CustomException(String message, HttpStatus httpStatus){
        this.message = message;
        this.httpStatus = httpStatus;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
    
    // http 상태코드를 반환하는 메서드
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
