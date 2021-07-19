package com.server.Dotori.exception.token.exception;

public class LogoutTokenException extends RuntimeException {

    public LogoutTokenException(String msg, Throwable t){
        super(msg, t);
    }
    public LogoutTokenException(String msg){
        super(msg);
    }
    public LogoutTokenException(){
        super();
    }
}
