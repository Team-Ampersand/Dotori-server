package com.server.Dotori.exception.customError.exception;

public class CustomUnauthorizedException extends RuntimeException{
    public CustomUnauthorizedException(String msg, Throwable t){
        super(msg, t);
    }
    public CustomUnauthorizedException(String msg){
        super(msg);
    }
    public CustomUnauthorizedException(){
        super();
    }
}
